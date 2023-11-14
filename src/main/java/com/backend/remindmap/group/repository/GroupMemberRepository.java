package com.remind.map.group.repository;

import com.remind.map.group.domain.GroupMember;
import com.remind.map.group.domain.GroupMemberDto;

public interface GroupMemberRepository {

    GroupMember addMember(GroupMemberDto groupMemberDto);

    void removeMember(Long id);

}
