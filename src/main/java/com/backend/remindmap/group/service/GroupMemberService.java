package com.backend.remindmap.group.service;


import com.backend.remindmap.group.domain.groupMember.GroupMember;
import com.backend.remindmap.group.domain.groupMember.GroupMemberDto;
import com.backend.remindmap.group.repository.groupMember.GroupMemberRepository;
import com.backend.remindmap.member.domain.Member.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class GroupMemberService {

    private final GroupMemberRepository groupMemberRepository;
    private final WaitingService waitingService;

    @Transactional
    public GroupMember addMember(GroupMemberDto groupMemberDto) {
        GroupMember groupMember = groupMemberRepository.addMember(groupMemberDto);
        waitingService.removeMemberToWaiting(groupMemberDto);
        return groupMember;
    }

    public void removeMember(GroupMemberDto groupMemberDto) {
        groupMemberRepository.removeMember(groupMemberDto);
    }

    public List<Member> findAllMember(Long groupId) {
        return groupMemberRepository.findAllMember(groupId);
    }
}
