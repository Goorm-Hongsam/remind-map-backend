package com.backend.remindmap.star.domain;

import com.backend.remindmap.marker.domain.Marker;
import com.backend.remindmap.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StarRepository extends JpaRepository<Star, Long> {

    List<Star> findByMember(Member member);

    boolean existsByMemberAndMarker(Member member, Marker marker);

    Optional<Star> findByMemberAndMarker(Member member, Marker marker);
}
