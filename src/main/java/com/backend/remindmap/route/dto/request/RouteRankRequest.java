package com.backend.remindmap.route.dto.request;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class RouteRankRequest {

    private String typeId;
    private String type = "route";

    @Builder
    private RouteRankRequest(String typeId, String type) {
        this.typeId = typeId;
        this.type = type;
    }

    public static RouteRankRequest from(Long routeId) {
        return RouteRankRequest.builder()
                .typeId(String.valueOf(routeId))
                .build();
    }
}
