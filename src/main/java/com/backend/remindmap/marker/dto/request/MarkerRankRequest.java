package com.backend.remindmap.marker.dto.request;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class MarkerRankRequest {

    private String typeId;
    private String type = "marker";

    @Builder
    private MarkerRankRequest(String id) {
        this.typeId = id;
    }

    public static MarkerRankRequest from(Long markerId) {
        return MarkerRankRequest.builder()
                .id(String.valueOf(markerId))
                .build();
    }
}
