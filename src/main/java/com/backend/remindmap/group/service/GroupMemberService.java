package com.remind.map.group.service;

import com.remind.map.group.domain.GroupMember;
import com.remind.map.group.domain.GroupMemberDto;
import com.remind.map.group.repository.GroupMemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class GroupMemberService {

    private final GroupMemberRepository groupMemberRepository;

    public GroupMember addMember(GroupMemberDto groupMemberDto) {
        GroupMember groupMember = groupMemberRepository.addMember(groupMemberDto);
        return groupMember;
    }

    public void removeMember(Long id) {
        groupMemberRepository.removeMember(id);
    }
}
