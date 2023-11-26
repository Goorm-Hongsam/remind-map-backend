package com.backend.remindmap.markerRoute.presentation;

import com.backend.remindmap.global.s3.S3UploadService;
import com.backend.remindmap.marker.dto.request.MarkerCreateRequest;
import com.backend.remindmap.marker.dto.request.MarkerLocationRequest;
import com.backend.remindmap.markerRoute.application.MarkerRouteService;
import com.backend.remindmap.markerRoute.dto.request.MarkerRouteCreateRequest;
import com.backend.remindmap.markerRoute.dto.response.IntegrativeMarkerRouteCreateResponse;
import com.backend.remindmap.member.domain.Member.Member;
import com.backend.remindmap.route.dto.response.RouteResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class MarkerRouteController {

    private final MarkerRouteService markerRouteService;
    private final S3UploadService uploadService;

    @GetMapping("/marker-route")
    public ResponseEntity<List<RouteResponse>> findAllByMarkerLocation(@ModelAttribute MarkerLocationRequest request) {
        List<RouteResponse> response = markerRouteService.findAllByMarkerLocation(request);
        return ResponseEntity.ok().body(response);
    }

    @PostMapping(path = "/marker-route/{groupId}", consumes= {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<IntegrativeMarkerRouteCreateResponse> save(
            @PathVariable final Long groupId,
            @RequestPart(value = "request") MarkerRouteCreateRequest request,
            @RequestPart(value = "file") MultipartFile multipartFile,
            HttpServletRequest servletRequest
    ) throws IOException {
        Member member = (Member) servletRequest.getAttribute("member");
        String imageUrl = uploadService.uploadFile("route", multipartFile);
        IntegrativeMarkerRouteCreateResponse response = markerRouteService.save(groupId, member.getMemberId(), request, imageUrl);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/route/group/{groupId}")
    public List<RouteResponse> findMarkersByGroup(
            @PathVariable final Long groupId
    ) {
        return markerRouteService.findRoutesByGroup(groupId);
    }

}
