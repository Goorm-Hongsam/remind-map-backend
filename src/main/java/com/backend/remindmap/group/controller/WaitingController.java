package com.backend.remindmap.group.controller;


import com.backend.remindmap.group.domain.groupMember.GroupMemberDto;
import com.backend.remindmap.group.domain.waiting.InvitedMemberDto;
import com.backend.remindmap.group.domain.waiting.WaitMemberDto;
import com.backend.remindmap.group.domain.waiting.WaitingResponse;
import com.backend.remindmap.group.service.WaitingService;
import com.backend.remindmap.member.domain.Member.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
public class WaitingController {

    private final WaitingService waitingService;

    @PostMapping("/invite/{groupId}")
    public void inviteMember(@RequestBody List<WaitMemberDto> invitedList, @PathVariable Long groupId,
                             HttpServletRequest request) {
        Member leader = (Member) request.getAttribute("member");

        List<InvitedMemberDto> list = new ArrayList<>();
        for (WaitMemberDto dto : invitedList) {
            list.add(new InvitedMemberDto(dto.getMemberId(), groupId, leader.getMemberId()));
        }
        waitingService.addMemberToWaiting(list);
    }

    @GetMapping("/invite/getall")
    public List<WaitingResponse> getAllList(HttpServletRequest request) {
        Member member = (Member) request.getAttribute("member");
        return waitingService.getWaitingList(member.getMemberId());
    }

    @GetMapping("/invite/refuse/{groupId}")
    public void refuseInvite(@PathVariable Long groupId, HttpServletRequest request) {
        Member member = (Member) request.getAttribute("member");
        GroupMemberDto groupMemberDto = new GroupMemberDto(groupId, member.getMemberId());
        waitingService.removeMemberToWaiting(groupMemberDto);
    }



}
