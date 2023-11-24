package com.backend.remindmap.group.service;

import com.backend.remindmap.group.domain.groupMember.GroupMemberDto;
import com.backend.remindmap.group.domain.waiting.InvitedMemberDto;
import com.backend.remindmap.group.domain.waiting.WaitingResponse;
import com.backend.remindmap.group.repository.waiting.WaitingRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class WaitingService {

    private final WaitingRepository waitingRepository;

    public void addMemberToWaiting(List<InvitedMemberDto> list) {
        waitingRepository.save(list);
    }

    public void removeMemberToWaiting(GroupMemberDto info) {
        waitingRepository.remove(info);
    }

    public List<WaitingResponse> getWaitingList(Long memberId) {
        return waitingRepository.findAll(memberId);
    }
}
