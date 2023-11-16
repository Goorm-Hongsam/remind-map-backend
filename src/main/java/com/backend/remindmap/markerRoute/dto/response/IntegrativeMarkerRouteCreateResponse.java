package com.backend.remindmap.markerRoute.dto.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class IntegrativeMarkerRouteCreateResponse {

    private Long routeId;
    List<MarkerRouteCreateResponse> markerRouteCreateResponses;

    public IntegrativeMarkerRouteCreateResponse(Long routeId, List<MarkerRouteCreateResponse> markerRouteCreateResponses) {
        this.routeId = routeId;
        this.markerRouteCreateResponses = markerRouteCreateResponses;
    }
}
