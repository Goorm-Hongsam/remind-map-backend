package com.backend.remindmap.marker.presentation;

import com.backend.remindmap.marker.application.MarkerService;
import com.backend.remindmap.marker.dto.request.MarkerCreateRequest;
import com.backend.remindmap.marker.dto.request.MarkerLocationRequest;
import com.backend.remindmap.marker.dto.request.MarkerUpdateRequest;
import com.backend.remindmap.marker.dto.response.MarkerResponse;
import com.backend.remindmap.member.domain.Member;
import lombok.RequiredArgsConstructor;
import org.locationtech.jts.io.ParseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class MarkerController {

    private final MarkerService markerService;

    @PostMapping("/marker")
    public ResponseEntity<MarkerResponse> save(
            @Valid @RequestBody final MarkerCreateRequest request,
            HttpServletRequest servletRequest
    ) throws ParseException {
        Member member = (Member) servletRequest.getAttribute("member");
        MarkerResponse response = markerService.save(member.getId(), request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/markers")
    public List<MarkerResponse> findMarkersByLocation(@ModelAttribute MarkerLocationRequest request) {
        return markerService.findMarkersByLocation(request);
    }

    @DeleteMapping("/marker/{markerId}")
    public ResponseEntity<Void> delete(@PathVariable final Long markerId) {
        markerService.delete(markerId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/marker/{markerId}")
    public ResponseEntity<MarkerResponse> findMarker(@PathVariable final Long markerId) {
        MarkerResponse response = markerService.findMarker(markerId);
        return ResponseEntity.ok().body(response);
    }

    @PatchMapping("/marker/{markerId}")
    public ResponseEntity<MarkerResponse> updateMarker(
            @PathVariable final Long markerId,
            @Valid @RequestBody final MarkerUpdateRequest request,
            HttpServletRequest servletRequest
    ) {
        Member member = (Member) servletRequest.getAttribute("member");
        MarkerResponse response = markerService.updateMarker(markerId, member.getId(), request);
        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/marker/group/{groupId}")
    public ResponseEntity<MarkerResponse> saveByGroup(
            @PathVariable final Long groupId,
            @Valid @RequestBody final MarkerCreateRequest request,
            HttpServletRequest servletRequest
    ) throws ParseException {
        Member member = (Member) servletRequest.getAttribute("member");
        MarkerResponse response = markerService.saveByGroup(member.getId(), groupId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/marker/group/{groupId}")
    public List<MarkerResponse> findMarkersByGroup(
            @PathVariable final Long groupId
    ) {
        return markerService.findMarkersByGroup(groupId);
    }
}
