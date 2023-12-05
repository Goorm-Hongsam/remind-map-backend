package com.backend.remindmap.member.domain.KakaoToken;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class KakaoMemberToken {

    @Id @GeneratedValue
    @Column(name = "kakao_member_token_id")
    private Long id;

    private Long memberId;
    private String kakaoAccessToken;
    private String kakaoRefreshToken;
}
