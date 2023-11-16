package com.backend.remindmap.star.presentation;


import com.backend.remindmap.marker.domain.Marker;
import com.backend.remindmap.marker.dto.response.MarkerResponse;
import com.backend.remindmap.member.domain.Member;
import com.backend.remindmap.star.application.StarService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("/v1/api")
@RestController
@RequiredArgsConstructor
public class StarController {

    private final StarService starService;

    @PostMapping("/star/markers/{markerId}")
    public ResponseEntity<Void> addMarkerToWishlist(
            @PathVariable final Long markerId,
            HttpServletRequest servletRequest) {
        Member member = (Member) servletRequest.getAttribute("member");
        starService.addMarkerToWishlist(member.getId(), markerId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/star")
    public ResponseEntity<List<MarkerResponse>> getWishlist(
            HttpServletRequest servletRequest) {
        Member member = (Member) servletRequest.getAttribute("member");
        List<Marker> wishlist = starService.getMemberWishlist(member.getId());
        List<MarkerResponse> responses = wishlist.stream()
                .map(Marker::toResponse)
                .collect(Collectors.toList());

        return ResponseEntity.ok(responses);
    }

    @DeleteMapping("/star/markers/{markerId}")
    public ResponseEntity<Void> removeMarkerFromWishlist(
            HttpServletRequest servletRequest,
            @PathVariable Long markerId) {
        Member member = (Member) servletRequest.getAttribute("member");
        starService.removeMarkerFromWishlist(member.getId(), markerId);
        return ResponseEntity.noContent().build();
    }
}
