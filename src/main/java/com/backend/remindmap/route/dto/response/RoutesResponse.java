package com.backend.remindmap.route.dto.response;

import com.backend.remindmap.route.domain.Route;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class RoutesResponse {
    private List<RouteResponse> routes;

    public RoutesResponse(List<RouteResponse> routes) {
        this.routes = routes;
    }

    public static RoutesResponse fromEntity(List<Route> routes) {
        List<RouteResponse> routeResponses = routes.stream()
                .map(route -> RouteResponse.fromEntityWithoutMarkers(route))  // markers를 null로 설정하였습니다. 필요하다면 로직을 추가해주세요.
                .collect(Collectors.toList());
        return new RoutesResponse(routeResponses);
    }
}
