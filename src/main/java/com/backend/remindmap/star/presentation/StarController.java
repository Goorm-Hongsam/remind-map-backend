package com.backend.remindmap.star.presentation;


import com.backend.remindmap.marker.domain.Marker;
import com.backend.remindmap.marker.dto.response.MarkerResponse;
import com.backend.remindmap.member.domain.Member;
import com.backend.remindmap.route.domain.Route;
import com.backend.remindmap.route.dto.response.RouteResponse;
import com.backend.remindmap.star.application.StarService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class StarController {

    private final StarService starService;

    @PostMapping("/star/markers/{markerId}")
    public ResponseEntity<Void> addMarkerToStar(
            @PathVariable final Long markerId,
            HttpServletRequest servletRequest) {
        Member member = (Member) servletRequest.getAttribute("member");
        starService.addMarkerToStar(member.getMemberId(), markerId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/star/markers")
    public ResponseEntity<List<MarkerResponse>> getStar(
            HttpServletRequest servletRequest) {
        Member member = (Member) servletRequest.getAttribute("member");
        List<Marker> wishlist = starService.getMemberStar(member.getMemberId());
        List<MarkerResponse> responses = wishlist.stream()
                .map(Marker::toResponse)
                .collect(Collectors.toList());

        return ResponseEntity.ok(responses);
    }

    @DeleteMapping("/star/markers/{markerId}")
    public ResponseEntity<Void> removeMarkerFromStar(
            HttpServletRequest servletRequest,
            @PathVariable Long markerId) {
        Member member = (Member) servletRequest.getAttribute("member");
        starService.removeMarkerFromStar(member.getMemberId(), markerId);
        return ResponseEntity.noContent().build();
    }


    @PostMapping("/star/routes/{routeId}")
    public ResponseEntity<Void> addRouteToStar(
            HttpServletRequest servletRequest,
            @PathVariable Long routeId) {
        Member member = (Member) servletRequest.getAttribute("member");
        starService.addRouteToStar(member.getMemberId(), routeId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/star/routes")
    public ResponseEntity<List<RouteResponse>> getStarRoutes(ServletRequest servletRequest) {
        Member member = (Member) servletRequest.getAttribute("member");
        List<Route> wishlistRoutes = starService.getMemberStarRoutes(member.getMemberId());
        List<RouteResponse> responses = wishlistRoutes.stream()
                .map(v-> RouteResponse.fromEntityWithoutMarkers(v))
                .collect(Collectors.toList());

        return ResponseEntity.ok(responses);
    }

    @DeleteMapping("/star/routes/{routeId}")
    public ResponseEntity<Void> removeRouteFromStar(
            HttpServletRequest servletRequest,
            @PathVariable Long routeId) {
        Member member = (Member) servletRequest.getAttribute("member");
        starService.removeRouteFromStar(member.getMemberId(), routeId);
        return ResponseEntity.noContent().build();
    }

}
