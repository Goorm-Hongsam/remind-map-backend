package com.backend.remindmap.group.controller;


import com.backend.remindmap.group.domain.group.Group;
import com.backend.remindmap.group.domain.group.GroupDto;
import com.backend.remindmap.member.domain.Member;
import com.backend.remindmap.group.service.GroupService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
public class GroupController {

    private final GroupService groupService;

    @PostMapping("/group/create")
    public Group createGroup(@RequestBody GroupDto groupDto) {
        Group group = groupService.createGroup(groupDto);
        return group;
    }

    @PostMapping("/group/edit/{groupId}")
    public void editGroup(@PathVariable Long groupId, @RequestBody GroupDto groupDto) {
        groupService.editGroup(groupId, groupDto);
    }

    @PostMapping("/group/remove/{groupId}")
    public Group removeGroup(@PathVariable Long groupId) {
        Group removedGroup = groupService.removeGroup(groupId);
        return removedGroup;
    }

    @GetMapping ("/group/get/{groupId}")
    public Group getGroup(@PathVariable Long groupId) {
        return groupService.findGroupById(groupId);
    }

    @GetMapping("/group/getall")
    public List<Group> getAllGroup(HttpServletRequest request) {
        Member member = (Member) request.getAttribute("member");
        return groupService.findAllGroup(member.getMemberId());
    }
}
