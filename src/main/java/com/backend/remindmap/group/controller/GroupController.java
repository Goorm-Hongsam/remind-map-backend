package com.remind.map.group.controller;

import com.remind.map.group.domain.Group;
import com.remind.map.group.domain.GroupDto;
import com.remind.map.group.service.GroupService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

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
    public List<Group> getAllGroup() {
        return groupService.findAllGroup();
    }
}
