package com.backend.remindmap.member.domain;

import lombok.*;

import javax.persistence.*;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Member {

    @Id
    @Column(name = "member_id")
    private Long id;

    private String nickname;
    private String thumbnailImageUrl;

    @Builder
    public Member(String nickname, String thumbnailImageUrl) {
        this.nickname = nickname;
        this.thumbnailImageUrl = thumbnailImageUrl;
    }

}
