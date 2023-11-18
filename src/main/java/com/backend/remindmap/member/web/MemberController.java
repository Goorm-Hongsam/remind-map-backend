package com.backend.remindmap.member.web;


import com.backend.remindmap.member.domain.KakaoFriendsDto;
import com.backend.remindmap.member.domain.KakaoTokenDto;
import com.backend.remindmap.member.domain.Member;
import com.backend.remindmap.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
@Slf4j
public class MemberController {

    private final MemberService memberService;

    // 카카오 로그인
    @PostMapping("/kakao/kakaoLogin/{code}")
    public ResponseEntity<KakaoFriendsDto> kakaoLogin(@PathVariable("code") String code) {

        log.info("인가코드={}",code);

        // 토큰 받기
        KakaoTokenDto kakaoAccessToken = memberService.getKakaoAccessToken(code);

        log.info("accessToken={}", kakaoAccessToken.getAccess_token());

        // 유서 정보 받기
        Member member = memberService.getMemberInfo(kakaoAccessToken.getAccess_token());

        // jwt 토큰 생성
        String jwtToken = memberService.getJwtToken(member);

        log.info(jwtToken);

        // 친구목록 테스트
        KakaoFriendsDto kakaoFriendsDto = memberService.getFriends(kakaoAccessToken.getAccess_token());

        // 친구에게 메세지 보내기 테스트
        memberService.sendMessage(kakaoAccessToken.getAccess_token());

        // 나에게 메세지 보내기 테스트
//        memberService.sendMessageToMe(kakaoAccessToken.getAccess_token());

        // 헤더에 jwt 토큰 담기
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + jwtToken);

        return ResponseEntity.ok().headers(headers).body(kakaoFriendsDto);
    }

    // 친구목록

    // jwt 필터 거친 후 정보 조회 예시
    @PostMapping("/test")
    public Member test(HttpServletRequest request) {

        Member member = (Member) request.getAttribute("member");

        return member;
    }



}
