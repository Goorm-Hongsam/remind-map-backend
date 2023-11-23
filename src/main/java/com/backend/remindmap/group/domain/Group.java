package com.backend.remindmap.group.domain;


import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long groupId;

    private String url; // 그룹 이미지 S3 URL

    private String title;

    public Group() {
    }

    public Group(String title) {
        this.title = title;
    }
}
