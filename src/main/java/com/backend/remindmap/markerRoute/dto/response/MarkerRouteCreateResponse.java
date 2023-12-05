package com.backend.remindmap.markerRoute.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class MarkerRouteCreateResponse {
    private Long id;

    @Builder
    public MarkerRouteCreateResponse(Long id) {
        this.id = id;
    }
}

