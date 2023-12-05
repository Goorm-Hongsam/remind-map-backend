package com.backend.remindmap.group.domain.waiting;

import lombok.Data;

import javax.persistence.Entity;

@Data
public class WaitingResponse {
    private String nickname;
    private String title;
    private Long groupId;
    private Long memberId;
    private Long leaderId;

    public WaitingResponse(String nickname, String title, Long groupId, Long memberId, Long leaderId) {
        this.nickname = nickname;
        this.title = title;
        this.groupId = groupId;
        this.memberId = memberId;
        this.leaderId = leaderId;
    }

    public WaitingResponse() {
    }

}
