package com.backend.remindmap.member.service;

import com.backend.remindmap.member.domain.InviteKakaoFriends.KakaoFriendsDto;
import com.backend.remindmap.member.domain.InviteKakaoFriends.MessageMemberGroupDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Service
@Slf4j
public class MemberInviteService {

    public KakaoFriendsDto getFriends(String token) {

        RestTemplate rt = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + token);

        HttpEntity<MultiValueMap<String, String>> accountInfoRequest = new HttpEntity<>(headers);

        // GET 방식으로 API 서버에 요청 후 response 받아옴
        ResponseEntity<String> accountInfoResponse = rt.exchange(
                "https://kapi.kakao.com/v1/api/talk/friends",
                HttpMethod.GET,
                accountInfoRequest,
                String.class
        );

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        KakaoFriendsDto kakaoFriendsDto = null;
        try {
            kakaoFriendsDto = objectMapper.readValue(accountInfoResponse.getBody(), KakaoFriendsDto.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return kakaoFriendsDto;

    }

    public void sendMessage(String accessToken,MessageMemberGroupDto messageMemberGroupDto) {

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);

        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        String[] uuids = messageMemberGroupDto.getUuid();
        ObjectMapper objectMapper = new ObjectMapper();
        String uuidsJson;

        try {
            uuidsJson = objectMapper.writeValueAsString(uuids);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error processing JSON", e);
        }

        ObjectMapper objectMapper_2 = new ObjectMapper();
        Map<String, Object> templateArgs = new HashMap<>();
        templateArgs.put("groupId", messageMemberGroupDto.getGroupId());
        templateArgs.put("groupName", messageMemberGroupDto.getGroupName());
        String templateArgsJson;

        try {
            templateArgsJson = objectMapper_2.writeValueAsString(templateArgs);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error processing JSON", e);
        }

        MultiValueMap<String, Object> params = new LinkedMultiValueMap<>();
        params.add("receiver_uuids", uuidsJson);
        params.add("template_id", "100915");
        params.add("template_args", templateArgsJson);

        HttpEntity<MultiValueMap<String, Object>> kakaoTokenRequest = new HttpEntity<>(params, headers);

        RestTemplate rt = new RestTemplate();
        ResponseEntity<String> accessTokenResponse = rt.exchange(
                "https://kapi.kakao.com/v1/api/talk/friends/message/send",
                HttpMethod.POST,
                kakaoTokenRequest,
                String.class
        );

    }

    // 나에게 보내기
    public void sendMessageToMe(String accessToken) {

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("template_id", "100915");

        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest = new HttpEntity<>(params, headers);

        RestTemplate rt = new RestTemplate();
        ResponseEntity<String> accessTokenResponse = rt.exchange(
                "https://kapi.kakao.com/v2/api/talk/memo/send",
                HttpMethod.POST,
                kakaoTokenRequest,
                String.class
        );
    }

}
