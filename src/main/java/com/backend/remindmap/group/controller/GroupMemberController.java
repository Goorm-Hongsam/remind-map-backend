package com.remind.map.group.controller;

import com.remind.map.group.domain.GroupMember;
import com.remind.map.group.domain.GroupMemberDto;
import com.remind.map.group.service.GroupMemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
@RestController
@RequiredArgsConstructor
@Slf4j
public class GroupMemberController {

    private final GroupMemberService groupMemberService;

    @PostMapping("/group/member/add")
    public GroupMember addMember(@RequestBody GroupMemberDto groupMemberDto) {
        GroupMember groupMember = groupMemberService.addMember(groupMemberDto);
        return groupMember;
    }

    @PostMapping("/group/member/remove/{gmId}")
    public void removeMember(@PathVariable Long gmId) {
        groupMemberService.removeMember(gmId);
    }

    // n번 그룹에 대한 그룹원 전체조회 만들기


}
