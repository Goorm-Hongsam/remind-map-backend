package com.backend.remindmap.group.controller;

import com.backend.remindmap.group.domain.groupMember.GroupMember;
import com.backend.remindmap.group.domain.groupMember.GroupMemberDto;
import com.backend.remindmap.member.domain.Member;
import com.backend.remindmap.group.service.GroupMemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class GroupMemberController {

    private final GroupMemberService groupMemberService;

    /**
     * 엔드포인트 수정 !
     */
    @GetMapping("/group/member/add/{groupId}")
    public GroupMember addMember(@PathVariable Long groupId ,HttpServletRequest request) {
        Member member = (Member) request.getAttribute("member");
        GroupMemberDto groupMemberDto = new GroupMemberDto(groupId, member.getMemberId());
        GroupMember groupMember = groupMemberService.addMember(groupMemberDto);
        return groupMember;
    }


    @DeleteMapping("/group/member/remove/{groupId}")
    public void removeMember(@PathVariable Long groupId, HttpServletRequest request) {
        Member member = (Member) request.getAttribute("member");
        GroupMemberDto groupMemberDto = new GroupMemberDto(groupId, member.getMemberId());
        groupMemberService.removeMember(groupMemberDto);
    } // 내가 이 그룹 탈퇴 !



    @GetMapping("group/member/get/{groupId}")
    public List<Member> getMember(@PathVariable Long groupId) {
        return groupMemberService.findAllMember(groupId);
    }

}
