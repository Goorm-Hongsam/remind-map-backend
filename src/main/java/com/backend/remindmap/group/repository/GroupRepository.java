package com.remind.map.group.repository;

import com.remind.map.group.domain.Group;
import com.remind.map.group.domain.GroupDto;
import com.remind.map.group.domain.GroupMember;

import java.util.List;
import java.util.Optional;

public interface GroupRepository {

    Group saveGroup(GroupDto groupDto);

    void updateGroup(Long id, GroupDto groupDto);

    Group removeGroup(Long id);

    Optional<Group> findGroupById(Long id);

    List<Group> findAllGroup();
}
