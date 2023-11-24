package com.backend.remindmap.member.domain.Member;

import lombok.*;

import javax.persistence.*;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Member {

    @Id
    @Column(name = "member_id")
    private Long memberId;

    private String nickname;
    private String thumbnailImageUrl;

    @Builder
    public Member(String nickname, String thumbnailImageUrl) {
        this.nickname = nickname;
        this.thumbnailImageUrl = thumbnailImageUrl;
    }

}
