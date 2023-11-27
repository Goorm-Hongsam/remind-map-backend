package com.backend.remindmap.marker.domain;

import com.backend.remindmap.group.domain.group.Group;
import com.backend.remindmap.marker.dto.response.MarkerResponse;
import com.backend.remindmap.member.domain.Member.Member;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.locationtech.jts.geom.Point;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "markers")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Marker {

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

    private String memo;

    private String imageUrl;

    @Embedded
    private Location location;

    @Column
    private Point point;

    @Column(nullable = false)
    private boolean visiable;

    @Column
    @ColumnDefault("0")
    private int view;

    @Column(name = "went_date", nullable = false)
    private LocalDate wentDate;

    @Builder
    public Marker(Member member, Group group, Long id, String title, String memo, String imageUrl, Location location, Point point, boolean visiable, LocalDate wentDate) {
        this.member = member;
        this.group = group;
        this.id = id;
        this.title = title;
        this.memo = memo;
        this.imageUrl = imageUrl;
        this.location = location;
        this.point = point;
        this.visiable = visiable;
        this.wentDate = wentDate;
    }

    public MarkerResponse toResponse() {
        return MarkerResponse.builder()
                .id(this.id)
                .title(this.title)
                .memo(this.memo)
                .imageUrl(this.imageUrl)
                .visiable(this.visiable)
                .location(this.location)
                .wentDate(this.wentDate)
                .build();
    }
}
