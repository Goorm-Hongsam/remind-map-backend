package com.backend.remindmap.group.domain.waiting;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
public class Waiting {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long waitId;
    private Long groupId;
    private Long memberId;
    private Long leaderId;

    public Waiting(Long groupId, Long memberId, Long leaderId) {
        this.groupId = groupId;
        this.memberId = memberId;
        this.leaderId = leaderId;
    }

    public Waiting() {
    }
}
