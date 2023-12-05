package com.backend.remindmap.group.repository.group;


import com.backend.remindmap.group.domain.group.Group;
import com.backend.remindmap.group.domain.group.GroupDto;

import java.util.List;
import java.util.Optional;

public interface GroupRepository {

    Group saveGroup(GroupDto groupDto);

    void updateGroup(Long id, GroupDto groupDto);

    Group removeGroup(Long id);

    Optional<Group> findGroupById(Long id);

    List<Group> findAllGroup(Long id);
}
