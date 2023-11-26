package com.backend.remindmap.route.domain;

import com.backend.remindmap.group.domain.group.Group;
import com.backend.remindmap.member.domain.Member.Member;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Table(name = "routes")
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Route {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id")
    private Group group;

    private String title;

    @Column
    private String memo;

    private String imageUrl;

    @Column(nullable = false)
    private boolean visiable;

    @Column
    @ColumnDefault("0")
    private int view;

    @Column(name = "went_date", nullable = false)
    private LocalDateTime wentDate;

    @Builder
    public Route(Member member, String title, String memo, String imageUrl, boolean visiable, int view, LocalDateTime wentDate) {
        this.member = member;
        this.title = title;
        this.memo = memo;
        this.imageUrl = imageUrl;
        this.visiable = visiable;
        this.view = view;
        this.wentDate = wentDate;
    }
}
