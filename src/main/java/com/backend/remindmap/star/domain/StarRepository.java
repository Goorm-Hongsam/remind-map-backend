package com.backend.remindmap.star.domain;

import com.backend.remindmap.marker.domain.Marker;
import com.backend.remindmap.member.domain.Member.Member;
import com.backend.remindmap.route.domain.Route;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StarRepository extends JpaRepository<Star, Long> {

    List<Star> findByMember(Member member);

    boolean existsByMemberAndMarker(Member member, Marker marker);

    Optional<Star> findByMemberAndMarker(Member member, Marker marker);

    boolean existsByMemberAndRoute(Member member, Route route);

    Optional<Star> findByMemberAndRoute(Member member, Route route);
}
