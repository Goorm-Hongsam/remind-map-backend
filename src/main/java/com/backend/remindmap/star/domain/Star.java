package com.backend.remindmap.star.domain;

import com.backend.remindmap.marker.domain.Marker;
import com.backend.remindmap.member.domain.Member;
import com.backend.remindmap.route.domain.Route;
import lombok.*;

import javax.persistence.*;

@Getter
@Entity
@Table(name = "stars")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Star {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "marker_id")
    private Marker marker;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "route_id")
    private Route route;

    @Builder
    public Star(Member member, Marker marker, Route route) {
        this.member = member;
        this.marker = marker;
        this.route = route;
    }
}
