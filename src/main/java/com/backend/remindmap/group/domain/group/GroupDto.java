package com.backend.remindmap.group.domain.group;

import lombok.Data;

@Data
public class GroupDto {
    private String title;

    public GroupDto(String title) {
        this.title = title;
    }
    public GroupDto() {
    }
}
