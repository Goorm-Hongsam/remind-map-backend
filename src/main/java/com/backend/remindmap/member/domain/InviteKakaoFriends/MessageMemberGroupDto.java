package com.backend.remindmap.member.domain.InviteKakaoFriends;

import lombok.Data;

@Data
public class MessageMemberGroupDto {

    private String[] uuid;
    private String groupId;
    private String groupName;
}
