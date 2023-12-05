package com.backend.remindmap.member.domain.InviteKakaoFriends;

import lombok.Data;

@Data
public class Friend {

    private Long id;
    private String uuid;
    private Boolean favorite;
    private String profile_nickname;
    private String profile_thumbnail_image;
}
