package com.backend.remindmap.markerRoute.presentation;

import com.backend.remindmap.marker.dto.request.MarkerLocationRequest;
import com.backend.remindmap.marker.dto.response.MarkerResponse;
import com.backend.remindmap.markerRoute.application.MarkerRouteService;
import com.backend.remindmap.markerRoute.dto.request.MarkerRouteCreateRequest;
import com.backend.remindmap.markerRoute.dto.response.IntegrativeMarkerRouteCreateResponse;
import com.backend.remindmap.markerRoute.dto.response.MarkerRouteResponse;
import com.backend.remindmap.member.domain.Member;
import com.backend.remindmap.route.dto.response.RouteResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class MarkerRouteController {

    private final MarkerRouteService markerRouteService;

    @PostMapping("/marker-route")
    public ResponseEntity<IntegrativeMarkerRouteCreateResponse> save(
            @Valid @RequestBody final MarkerRouteCreateRequest request,
            HttpServletRequest servletRequest
    ) {
        Member member = (Member) servletRequest.getAttribute("member");
        IntegrativeMarkerRouteCreateResponse response = markerRouteService.save(member.getId(), request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/marker-route")
    public ResponseEntity<List<RouteResponse>> findAllByMarkerLocation(
            @Valid @ModelAttribute final MarkerLocationRequest request,
            HttpServletRequest servletRequest
    ) {
        List<RouteResponse> response = markerRouteService.findAllByMarkerLocation(request);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/route/group/{groupId}")
    public List<RouteResponse> findMarkersByGroup(
            @PathVariable final Long groupId
    ) {
        return markerRouteService.findRoutesByGroup(groupId);
    }


}
