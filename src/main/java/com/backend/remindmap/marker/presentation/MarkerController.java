package com.backend.remindmap.marker.presentation;

import com.backend.remindmap.marker.application.MarkerProducerService;
import com.backend.remindmap.marker.application.MarkerService;
import com.backend.remindmap.marker.dto.request.MarkerCreateRequest;
import com.backend.remindmap.marker.dto.request.MarkerLocationRequest;
import com.backend.remindmap.marker.dto.request.MarkerRankRequest;
import com.backend.remindmap.marker.dto.response.MarkerResponse;
import com.backend.remindmap.member.domain.Member.Member;
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
    private final MarkerProducerService markerProducerService;

    @PostMapping("/marker")
    public ResponseEntity<MarkerResponse> save(
            @Valid @RequestBody final MarkerCreateRequest request,
            HttpServletRequest servletRequest
    ) throws ParseException {
        Member member = (Member) servletRequest.getAttribute("member");
        MarkerResponse response = markerService.save(member.getMemberId(), request);
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
        markerProducerService.send(MarkerRankRequest.from(markerId));
        return ResponseEntity.ok().body(response);
    }
}
