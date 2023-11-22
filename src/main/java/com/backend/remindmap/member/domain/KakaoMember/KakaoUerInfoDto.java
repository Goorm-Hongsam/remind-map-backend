package com.backend.remindmap.member.domain.KakaoMember;

import com.backend.remindmap.member.domain.KakaoMember.KaKaoAccount;
import lombok.Data;

@Data
public class KakaoUerInfoDto {

    private Long id;
    private KaKaoAccount kakao_account;
}
