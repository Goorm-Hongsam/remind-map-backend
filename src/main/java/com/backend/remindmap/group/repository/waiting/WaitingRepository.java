package com.backend.remindmap.group.repository.waiting;

import com.backend.remindmap.group.domain.groupMember.GroupMemberDto;
import com.backend.remindmap.group.domain.waiting.InvitedMemberDto;
import com.backend.remindmap.group.domain.waiting.WaitingResponse;

import java.util.List;

public interface WaitingRepository {

    void save(List<InvitedMemberDto> list);

    void remove(GroupMemberDto groupMemberDto);

    List<WaitingResponse> findAll(Long memberId);
}
