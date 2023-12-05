package com.backend.remindmap.member.web;


import com.backend.remindmap.Config.JWT.JwtTokenProvider;
import com.backend.remindmap.member.domain.KakaoToken.KakaoTokenDto;
import com.backend.remindmap.member.domain.Member.Member;
import com.backend.remindmap.member.repository.MemberRepository;
import com.backend.remindmap.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@Slf4j
public class MemberController {

    private final MemberService memberService;
    private final JwtTokenProvider jwtTokenProvider;
    private final MemberRepository memberRepository;

    /**
     * 카카오 로그인
     * 로컬 테스트일 때만 Get 요청으로 변경해둠
     *
     */
    @PostMapping("/kakao/kakaoLogin/{code}")
    public ResponseEntity<Member> kakaoLogin(@PathVariable("code") String code, HttpServletResponse response) {

        log.info("인가코드={}",code);

        // kakao 토큰 받기
        KakaoTokenDto kakaoTokenDto = memberService.getKakaoAccessToken(code);

        log.info("kakao accessToken={}", kakaoTokenDto.getAccess_token());

        // 유서 정보 받기
        Member member = memberService.getMemberInfo(kakaoTokenDto.getAccess_token());

        // 카카오 token db 저장
        memberService.saveKakaoToken(member, kakaoTokenDto);

//         자체 access token 생성
        String accessToken = jwtTokenProvider.createAccessToken(member);

        log.info("자체 access token={}",accessToken);

//         자체 refresh token
        String refreshToken = jwtTokenProvider.createRefreshToken(member);

        // refresh token DB 저장
        memberService.saveRefreshToken(member,refreshToken);

        log.info("자체 refresh token={}",refreshToken);

        // localhost 테스트용 쿠키
//        ResponseCookie cookie = ResponseCookie.from("refresh-token",refreshToken)
//                .maxAge(14 * 24 * 60 * 60)
//                .path("/")
//                .httpOnly(true)
//                .build();

        /**
         * 배포용 쿠키
         */
        ResponseCookie cookie = ResponseCookie.from("refresh-token",refreshToken)
                .maxAge(14 * 24 * 60 * 60)
//                .maxAge(4 * 60) // 테스트용 4분
                .path("/")
                .sameSite("none") // 배포에서는 None
                .secure(true)
                .httpOnly(true)
                .build();
//         만약 에러나면 domain 등록

        // 친구목록 테스트
//        KakaoFriendsDto kakaoFriendsDto = memberService.getFriends(kakaoAccessToken.getAccess_token());

        // 친구에게 메세지 보내기 테스트
//        memberService.sendMessage(kakaoAccessToken.getAccess_token());

        // 나에게 메세지 보내기 테스트
//        memberService.sendMessageToMe(kakaoAccessToken.getAccess_token());

        // 헤더에 jwt 토큰 담기
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);

        response.setHeader("Set-Cookie", cookie.toString());

        return ResponseEntity.ok().headers(headers).body(member);
    }

    /**
     * access token 갱신
     * 클라이언트에서 받은 refresh token 유효성 검사
     * localhost 테스트에서 GET -> 배포 후 POST로 바꾸기
     */
    @PostMapping("/login-check/refresh-token")
    public void changeToken(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String refreshToken = Arrays.stream(request.getCookies())
                .filter(c -> c.getName().equals("refresh-token"))
                .findFirst() .map(Cookie::getValue)
                .orElse(null);

        log.info("헤더에서 넘어온 refresh token={}",refreshToken);

        // 유효성 검사
        if (!jwtTokenProvider.validateToken(refreshToken)) {
            /**
             * 서버에서 토큰 만료 시키기
             */
            response.sendError(HttpStatus.UNAUTHORIZED.value(), "로그아웃 해주세요.");
            return;
        }

        Long memberId = jwtTokenProvider.getMemberIdByRefreshToken(refreshToken);

        String dbRefreshToken = memberService.getDbRefreshToken(memberId).getRefreshToken();

        log.info("db에서 꺼낸 refresh token={}",dbRefreshToken);

        // db 비교 (해야할까?) -> 질문
        if ( !refreshToken.equals(dbRefreshToken)) {
            /**
             * 서버에서 토큰 만료 시키기
             */
            response.sendError(HttpStatus.UNAUTHORIZED.value(), "로그아웃 해주세요.");
            return;
        }

        Optional<Member> member = memberRepository.findMemberById(memberId);

        /**
         * 나중에 질문
         */
        if (member.isPresent()) {
            Member m = member.get();

            String newAccessToken = jwtTokenProvider.createAccessToken(m);
            String newRefreshToken = jwtTokenProvider.createRefreshToken(m);

            memberService.saveRefreshToken(m,newRefreshToken);

            log.info("새로운 토큰 생성");

            response.setHeader("Authorization", "Bearer " + newAccessToken);

            // localhost 테스트용 쿠키
            ResponseCookie cookie = ResponseCookie.from("refresh-token",newRefreshToken)
                    .maxAge(14 * 24 * 60 * 60)
//                    .maxAge(4 * 60)
                    .path("/")
                    .sameSite("none")
                    .secure(true)
                    .httpOnly(true)
                    .build();

            response.setHeader("Set-Cookie", cookie.toString());

        } else {
            response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "여기서 에러가..");
        }

    }

    /**
     * 새로고침시 access token 유효성 검사
     */
    @PostMapping("/login-check")
    public Member loginCheck(HttpServletRequest request) {
        return (Member) request.getAttribute("member");
    }

    /**
     * 로그아웃
     */
    @PostMapping("/out")
    public ResponseEntity<?> logout(HttpServletRequest request) {

        Member member = (Member) request.getAttribute("member");

        // 카카오 토큰 만료 시키기
        memberService.expireKakaoToken(member.getMemberId());
        log.info("카카오 로그아웃 - 카카오 토큰 만료 시키기");
        // 자체 refresh token 지우기
        memberService.deleteDbRefreshToken(member.getMemberId());
        log.info("db refresh token 지우기");
        // 카카오 토큰 저장 지우기
        memberService.deleteDbKakaoToken(member.getMemberId());
        log.info("db kakao token 지우기");

        return ResponseEntity.ok().build();

    }



    // jwt 필터 거친 후 정보 조회 예시
    // localhost 테스트에서 GET으로 -> 헤더에 보내주는건 POST
//    @GetMapping("/api/test")
    public Member test(HttpServletRequest request) {

        String token = Arrays.stream(request.getCookies())
                .filter(c -> c.getName().equals("refresh-token"))
                .findFirst() .map(Cookie::getValue)
                .orElse(null);

        log.info("꺼낸 refresh token={}",token);


        return (Member) request.getAttribute("member");

    }



}
