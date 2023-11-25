package com.backend.remindmap.group.domain.groupMember;


import lombok.Data;

@Data
public class GroupMemberDto {
    private Long groupId;
    private Long memberId;

    public GroupMemberDto(Long groupId, Long memberId) {
        this.groupId = groupId;
        this.memberId = memberId;
    }

    public GroupMemberDto() {
    }
}
