package com.backend.remindmap.group.repository.groupMember;


import com.backend.remindmap.group.domain.groupMember.GroupMember;
import com.backend.remindmap.group.domain.groupMember.GroupMemberDto;
import com.backend.remindmap.member.domain.Member;

import java.util.List;

public interface GroupMemberRepository {

    GroupMember addMember(GroupMemberDto groupMemberDto);

    void removeMember(GroupMemberDto groupMemberDto);

    List<Member> findAllMember(Long groupId);
}
