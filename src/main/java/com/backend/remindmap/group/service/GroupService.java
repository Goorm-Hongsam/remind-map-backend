package com.backend.remindmap.group.service;

import com.backend.remindmap.group.domain.Group;
import com.backend.remindmap.group.repository.GroupRepository;
import com.remind.map.group.domain.GroupDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class GroupService {

    private final GroupRepository groupRepository;

    public Group createGroup(GroupDto groupDto) {
        Group group = groupRepository.saveGroup(groupDto);
        log.info("create group : {}", group);
        return group;
    }

    public void editGroup(Long id, GroupDto groupDto) {
        groupRepository.updateGroup(id, groupDto);
        log.info("edit groupId = {}, Dto = {}", id, groupDto);
    }

    public Group removeGroup(Long id) {
        Group removedGroup = groupRepository.removeGroup(id);
        log.info("remove groupId = {}", id);
        return removedGroup;
    }

    public Group findGroupById(Long id) {
        Optional<Group> group = groupRepository.findGroupById(id);
        if(group.isPresent()) {
            log.info("success find, object = {}", group.get());
            return group.get();
        }
        else {
            log.info("failed find");
            return null;
        }

    }

    public List<Group> findAllGroup() {
        List<Group> allGroup = groupRepository.findAllGroup();
        return allGroup;
    }
}
