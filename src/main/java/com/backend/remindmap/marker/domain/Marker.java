package com.backend.remindmap.marker.domain;

import com.backend.remindmap.marker.dto.response.MarkerResponse;
import com.backend.remindmap.member.domain.Member.Member;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.locationtech.jts.geom.Point;

import javax.persistence.*;
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

    private String title;

    private String memo;

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
    private LocalDateTime wentDate;

    @Builder
    public Marker(Member member, Long id, String title, String memo, Location location, Point point, LocalDateTime wentDate) {
        this.member = member;
        this.id = id;
        this.title = title;
        this.memo = memo;
        this.location = location;
        this.point = point;
        this.wentDate = wentDate;
    }

    public MarkerResponse toResponse() {
        return MarkerResponse.builder()
                .id(this.id)
                .title(this.title)
                .memo(this.memo)
                .location(this.location)
                .wentDate(this.wentDate)
                .build();
    }
}