package com.backend.remindmap.group.domain.waiting;

import lombok.Data;

@Data
public class InvitedMemberDto {
    private Long memberId;
    private Long groupId;
    private Long leaderId;

    public InvitedMemberDto(Long memberId, Long groupId, Long leaderId) {
        this.memberId = memberId;
        this.groupId = groupId;
        this.leaderId = leaderId;
    }

    public InvitedMemberDto() {
    }
}
