package com.backend.remindmap.star.application;

import com.backend.remindmap.marker.domain.Marker;
import com.backend.remindmap.marker.domain.MarkerRepository;
import com.backend.remindmap.member.domain.Member;
import com.backend.remindmap.member.repository.MemberRepository;
import com.backend.remindmap.route.domain.Route;
import com.backend.remindmap.route.domain.RouteRepository;
import com.backend.remindmap.star.domain.Star;
import com.backend.remindmap.star.domain.StarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Transactional
@RequiredArgsConstructor
@Service
public class StarService {

    private final MemberRepository memberRepository;
    private final StarRepository starRepository;
    private final MarkerRepository markerRepository;
    private final RouteRepository routeRepository;

    public void addMarkerToStar(Long memberId, Long markerId) {
        Member member = memberRepository.findMemberById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));

        Marker marker = markerRepository.findById(markerId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 마커입니다."));

        if (starRepository.existsByMemberAndMarker(member, marker)) {
            throw new IllegalStateException("찜 목록 내 존재하지 않는 마커입니다.");
        }

        Star star = Star.builder()
                .member(member)
                .marker(marker)
                .build();
        starRepository.save(star);
    }

    @Transactional(readOnly = true)
    public List<Marker> getMemberStar(Long memberId) {
        Member member = memberRepository.findMemberById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));

        return starRepository.findByMember(member).stream()
                .map(Star::getMarker)
                .filter(Objects::nonNull) // Filter out null values if a Star is for a Route
                .collect(Collectors.toList());
    }

    public void removeMarkerFromStar(Long memberId, Long markerId) {
        Member member = memberRepository.findMemberById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));

        Marker marker = markerRepository.findById(markerId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 마커입니다."));

        starRepository.findByMemberAndMarker(member, marker)
                .ifPresent(starRepository::delete);
    }

    public void addRouteToStar(Long memberId, Long routeId) {
        Member member = memberRepository.findMemberById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));

        Route route = routeRepository.findById(routeId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 루트입니다."));

        if (starRepository.existsByMemberAndRoute(member, route)) {
            throw new IllegalStateException("찜 목록 내 존재하지 않는 루트입니다.");
        }

        Star star = Star.builder()
                .member(member)
                .route(route)
                .build();
        starRepository.save(star);
    }

    @Transactional(readOnly = true)
    public List<Route> getMemberStarRoutes(Long memberId) {
        Member member = memberRepository.findMemberById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));

        return starRepository.findByMember(member).stream()
                .map(Star::getRoute)
                .filter(Objects::nonNull) // Filter out null values if a Star is for a Marker
                .collect(Collectors.toList());
    }

    public void removeRouteFromStar(Long memberId, Long routeId) {
        Member member = memberRepository.findMemberById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));

        Route route = routeRepository.findById(routeId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 루트입니다."));

        Star star = starRepository.findByMemberAndRoute(member, route)
                .orElseThrow(() -> new IllegalStateException("찜 목록 내 존재하지 않는 루트입니다."));
        starRepository.delete(star);
    }

}
