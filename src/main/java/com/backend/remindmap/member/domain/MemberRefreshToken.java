package com.backend.remindmap.member.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class MemberRefreshToken {

    @Id
    @GeneratedValue
    private Long refreshTokenId;

    private Long memberId;
    private String refreshToken;

}
