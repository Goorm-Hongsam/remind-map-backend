package com.backend.remindmap.member.service;

import com.backend.remindmap.member.domain.KakaoMember.KakaoUerInfoDto;
import com.backend.remindmap.member.domain.KakaoToken.KakaoMemberToken;
import com.backend.remindmap.member.domain.KakaoToken.KakaoTokenDto;
import com.backend.remindmap.member.domain.Member.Member;
import com.backend.remindmap.member.domain.Member.MemberRefreshToken;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.backend.remindmap.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.*;


@RequiredArgsConstructor
@Service
@Slf4j
@Transactional
public class MemberService {

    private final MemberRepository memberRepository;

    @Value("${jwt.secret}")
    private String jwtSecretKey;

    @Value("${kakao.client_id}")
    private String client_id;

    /**
     * 카카오에서 토큰 받기
     */
    @Transactional
    public KakaoTokenDto getKakaoAccessToken(String code) {

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", client_id);
        params.add("redirect_uri", "https://remindmap.site/kakao/callback");
        params.add("code", code);
//        params.add("client_secret", KAKAO_CLIENT_SECRET); 선택 사항

        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest = new HttpEntity<>(params, headers);

        RestTemplate rt = new RestTemplate();
        ResponseEntity<String> accessTokenResponse = rt.exchange(
                "https://kauth.kakao.com/oauth/token",
                HttpMethod.POST,
                kakaoTokenRequest,
                String.class
        );

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        KakaoTokenDto kakaoTokenDto = null;
        try {
            kakaoTokenDto = objectMapper.readValue(accessTokenResponse.getBody(), KakaoTokenDto.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return kakaoTokenDto;
    }


    public Member getMemberInfo(String kakaoAccessToken) {

        RestTemplate rt = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + kakaoAccessToken);
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        HttpEntity<MultiValueMap<String, String>> accountInfoRequest = new HttpEntity<>(headers);

        // POST 방식으로 API 서버에 요청 후 response 받아옴
        ResponseEntity<String> accountInfoResponse = rt.exchange(
                "https://kapi.kakao.com/v2/user/me",
                HttpMethod.POST,
                accountInfoRequest,
                String.class
        );

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        KakaoUerInfoDto kakaoUerInfoDto = null;
        try {
            kakaoUerInfoDto = objectMapper.readValue(accountInfoResponse.getBody(), KakaoUerInfoDto.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        Member member = new Member(kakaoUerInfoDto.getId()
                ,kakaoUerInfoDto.getKakao_account().getProfile().getNickname()
                ,kakaoUerInfoDto.getKakao_account().getProfile().getThumbnail_image_url());

        log.info("카카오에서 가져온 id={}",kakaoUerInfoDto.getId());
        log.info("카카오에서 가져온 nickname={}",kakaoUerInfoDto.getKakao_account().getProfile().getNickname());
        log.info("카카오에서 가져온 img={}",kakaoUerInfoDto.getKakao_account().getProfile().getThumbnail_image_url());


        Optional<Member> existOwner = memberRepository.findMemberById(member.getMemberId());

        if (existOwner.isEmpty()) {
            // 회원가입
            memberRepository.save(member);
        }

        return member;

    }

    // 더티채킹
    @Transactional
    public void saveRefreshToken(Member member, String refreshToken) {

        MemberRefreshToken memberRefreshToken = new MemberRefreshToken();
        memberRefreshToken.setMemberId(member.getMemberId());
        memberRefreshToken.setRefreshToken(refreshToken);

        MemberRefreshToken dbRefreshToken = getDbRefreshToken(member.getMemberId());

        if (dbRefreshToken != null) {
            dbRefreshToken.setRefreshToken(refreshToken);
        } else {
            memberRepository.saveRefreshToken(memberRefreshToken);
        }

    }

    public MemberRefreshToken getDbRefreshToken(Long memberId) {

        Optional<MemberRefreshToken> memberRefreshToken = memberRepository.findRefreshTokenByMemberId(memberId);

        if (memberRefreshToken.isPresent()) {
            return memberRefreshToken.get();
        } else {
            return null;
        }

    }

    public void saveKakaoToken(Member member, KakaoTokenDto kakaoTokenDto) {

        KakaoMemberToken kakaoMemberToken = new KakaoMemberToken();
        kakaoMemberToken.setMemberId(member.getMemberId());
        kakaoMemberToken.setKakaoAccessToken(kakaoTokenDto.getAccess_token());
        kakaoMemberToken.setKakaoRefreshToken(kakaoTokenDto.getRefresh_token());

        KakaoMemberToken dbKakaoMemberToken = getDbKakaoMemberToken(member.getMemberId());

        if (dbKakaoMemberToken != null) {
            dbKakaoMemberToken.setKakaoAccessToken(kakaoTokenDto.getAccess_token());
            dbKakaoMemberToken.setKakaoRefreshToken(kakaoTokenDto.getRefresh_token());
        } else {
            memberRepository.saveKakaoToken(kakaoMemberToken);

        }
    }

    public KakaoMemberToken getDbKakaoMemberToken(Long memberId) {
        Optional<KakaoMemberToken> kakaoMemberToken = memberRepository.findKakaoMemberTokenByMemberId(memberId);

        if (kakaoMemberToken.isPresent()) {
            return kakaoMemberToken.get();
        } else {
            return null;
        }
    }

    // 로그아웃
//    public void logout(String kakaoAccessToken) {
//
//        RestTemplate rt = new RestTemplate();
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.add("Authorization", "Bearer " + kakaoAccessToken);
//        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
//
//        HttpEntity<MultiValueMap<String, String>> accountInfoRequest = new HttpEntity<>(headers);
//
//        // POST 방식으로 API 서버에 요청 후 response 받아옴
//        ResponseEntity<String> accountInfoResponse = rt.exchange(
//                "https://kapi.kakao.com/v2/user/me",
//                HttpMethod.POST,
//                accountInfoRequest,
//                String.class
//        );
//
//        ObjectMapper objectMapper = new ObjectMapper();
//        objectMapper.registerModule(new JavaTimeModule());
//        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
//        KakaoUerInfoDto kakaoUerInfoDto = null;
//        try {
//            kakaoUerInfoDto = objectMapper.readValue(accountInfoResponse.getBody(), KakaoUerInfoDto.class);
//        } catch (JsonProcessingException e) {
//            e.printStackTrace();
//        }
//    }
}

