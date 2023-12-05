package com.backend.remindmap.group.domain.groupMember;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
public class GroupMember {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long groupMemberId;

    private Long groupId;

    private Long memberId;

    public GroupMember() {
    }

    public GroupMember(Long groupId, Long memberId) {
        this.groupId = groupId;
        this.memberId = memberId;
    }

}
