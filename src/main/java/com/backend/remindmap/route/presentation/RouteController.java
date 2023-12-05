package com.backend.remindmap.route.presentation;

import com.backend.remindmap.route.application.RouteProducerService;
import com.backend.remindmap.route.application.RouteService;
import com.backend.remindmap.route.dto.request.RouteRankRequest;
import com.backend.remindmap.route.dto.response.RouteResponse;
import com.backend.remindmap.route.dto.response.RoutesResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class RouteController {

    private final RouteService routeService;
    private final RouteProducerService routeProducerService;

    @GetMapping("/route/{routeId}")
    public ResponseEntity<RouteResponse> findRouteWithMarkers(@PathVariable final Long routeId) {
        RouteResponse routeWithMarkers = routeService.findRouteWithMarkers(routeId);
        routeProducerService.send(RouteRankRequest.from(routeId));
        return ResponseEntity.ok().body(routeWithMarkers);
    }

    @GetMapping("/routes")
    public ResponseEntity<RoutesResponse> findAllRoutes() {
        RoutesResponse routesResponse = routeService.findAllRoutes();
        return ResponseEntity.ok().body(routesResponse);
    }

}
