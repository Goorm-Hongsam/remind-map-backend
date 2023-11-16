package com.backend.remindmap.star.application;

import com.backend.remindmap.marker.domain.Marker;
import com.backend.remindmap.marker.domain.MarkerRepository;
import com.backend.remindmap.member.domain.Member;
import com.backend.remindmap.member.repository.MemberRepository;
import com.backend.remindmap.star.domain.Star;
import com.backend.remindmap.star.domain.StarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class StarService {

    private final MemberRepository memberRepository;
    private final StarRepository starRepository;
    private final MarkerRepository markerRepository;

    @Transactional
    public void addMarkerToWishlist(Long memberId, Long markerId) {
        Member member = memberRepository.findMemberById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));

        Marker marker = markerRepository.findById(markerId)
                .orElseThrow(() -> new IllegalArgumentException("Marker not found"));

        // Check if already in wishlist
        if (starRepository.existsByMemberAndMarker(member, marker)) {
            throw new IllegalStateException("Marker already in wishlist");
        }

        Star star = Star.builder()
                .member(member)
                .marker(marker)
                .build();
        starRepository.save(star);
    }

    public List<Marker> getMemberWishlist(Long memberId) {
        Member member = memberRepository.findMemberById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));

        return starRepository.findByMember(member).stream()
                .map(Star::getMarker)
                .filter(Objects::nonNull) // Filter out null values if a Star is for a Route
                .collect(Collectors.toList());
    }

    @Transactional
    public void removeMarkerFromWishlist(Long memberId, Long markerId) {
        Member member = memberRepository.findMemberById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));

        Marker marker = markerRepository.findById(markerId)
                .orElseThrow(() -> new IllegalArgumentException("Marker not found"));

        starRepository.findByMemberAndMarker(member, marker)
                .ifPresent(starRepository::delete);
    }

}
