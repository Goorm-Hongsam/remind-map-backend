package com.backend.remindmap.group.repository;


import com.backend.remindmap.group.domain.Group;
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
