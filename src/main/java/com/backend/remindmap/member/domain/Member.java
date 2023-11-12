package com.backend.remindmap.member.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;


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
}
