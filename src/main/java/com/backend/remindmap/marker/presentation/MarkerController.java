package com.backend.remindmap.marker.presentation;

import com.backend.remindmap.global.s3.S3UploadService;
import com.backend.remindmap.marker.application.MarkerProducerService;
import com.backend.remindmap.marker.application.MarkerService;
import com.backend.remindmap.marker.dto.request.MarkerCreateRequest;
import com.backend.remindmap.marker.dto.request.MarkerLocationRequest;
import com.backend.remindmap.marker.dto.request.MarkerRankRequest;
import com.backend.remindmap.marker.dto.request.MarkerUpdateRequest;
import com.backend.remindmap.marker.dto.response.MarkerResponse;
import com.backend.remindmap.member.domain.Member.Member;
import lombok.RequiredArgsConstructor;
import org.locationtech.jts.io.ParseException;
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
public class MarkerController {

    private final MarkerService markerService;
    private final MarkerProducerService markerProducerService;
    private final S3UploadService uploadService;

    @GetMapping("/markers")
    public List<MarkerResponse> findMarkersByLocation(
            @ModelAttribute MarkerLocationRequest request,
            HttpServletRequest servletRequest
    ) {
        Member member = (Member) servletRequest.getAttribute("member");
        return markerService.findMarkersByLocation(member.getMemberId(), request);
    }

    @GetMapping("/marker/{markerId}")
    public ResponseEntity<MarkerResponse> findMarker(@PathVariable final Long markerId) {
        MarkerResponse response = markerService.findMarker(markerId);
        markerProducerService.send(MarkerRankRequest.from(markerId));
        return ResponseEntity.ok().body(response);
    }

    @PostMapping(path = "/marker/group/{groupId}", consumes= {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<MarkerResponse> saveByGroup(
            @PathVariable final Long groupId,
            @RequestPart(value = "request") MarkerCreateRequest request,
            @RequestPart(value = "file", required = false) MultipartFile multipartFile,
            HttpServletRequest servletRequest
    ) throws ParseException, IOException {
        Member member = (Member) servletRequest.getAttribute("member");
        String imageUrl = uploadService.uploadFile("marker", multipartFile);
        MarkerResponse response = markerService.saveByGroup(member.getMemberId(), groupId, request, imageUrl);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/marker/group/{groupId}")
    public List<MarkerResponse> findMarkersByGroup(
            @PathVariable final Long groupId
    ) {
        return markerService.findMarkersByGroup(groupId);
    }

    @PostMapping("/marker/update/group/{groupId}")
    public MarkerResponse updateMarker(
            @PathVariable final Long groupId,
            @RequestPart(value = "request") MarkerUpdateRequest request,
            @RequestPart(value = "file", required = false) MultipartFile multipartFile,
            HttpServletRequest servletRequest
    ) throws IOException {
        Member member = (Member) servletRequest.getAttribute("member");
        markerService.validateMarkerOwner(member.getMemberId(), request.getMarkerId());
        String imageUrl = uploadService.uploadFile("marker", multipartFile);
        return markerService.updateMarker(groupId, request, imageUrl);
    }

    @DeleteMapping("/marker/group/{groupId}/marker/{markerId}")
    public ResponseEntity<Void> delete(
            @PathVariable final Long groupId,
            @PathVariable final Long markerId,
            HttpServletRequest servletRequest
    ) {
        Member member = (Member) servletRequest.getAttribute("member");
        markerService.validateMarkerOwner(member.getMemberId(), markerId);
        markerService.delete(groupId, markerId);
        return ResponseEntity.noContent().build();
    }

}
