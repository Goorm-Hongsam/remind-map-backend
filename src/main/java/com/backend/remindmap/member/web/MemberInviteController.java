package com.backend.remindmap.member.web;

import com.backend.remindmap.member.domain.InviteKakaoFriends.KakaoFriendsDto;
import com.backend.remindmap.member.domain.InviteKakaoFriends.MessageMemberGroupDto;
import com.backend.remindmap.member.domain.KakaoToken.KakaoMemberToken;
import com.backend.remindmap.member.domain.Member.Member;
import com.backend.remindmap.member.service.MemberInviteService;
import com.backend.remindmap.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping("/group")
public class MemberInviteController {

    private final MemberService memberService;
    private final MemberInviteService memberInviteService;

    // 친구목록
    @GetMapping("/friends")
    public KakaoFriendsDto getKakaoFriends(HttpServletRequest request) {

        Member member = (Member) request.getAttribute("member");
        KakaoMemberToken kakaoMemberToken = memberService.getDbKakaoMemberToken(member.getMemberId());

        return memberInviteService.getFriends(kakaoMemberToken.getKakaoAccessToken());

    }

    // 메세지 보내기
    @PostMapping("/messages")
    public void sendMessages(HttpServletRequest request, @RequestBody MessageMemberGroupDto messageMemberGroupDto) {

        Member member = (Member) request.getAttribute("member");
        KakaoMemberToken kakaoMemberToken = memberService.getDbKakaoMemberToken(member.getMemberId());

        memberInviteService.sendMessage(kakaoMemberToken.getKakaoAccessToken(), messageMemberGroupDto);

    }
}
