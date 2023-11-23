package com.backend.remindmap.route.presentation;

import com.backend.remindmap.route.application.RouteService;
import com.backend.remindmap.route.dto.response.RouteResponse;
import com.backend.remindmap.route.dto.response.RoutesResponse;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class RouteController {

    private final RouteService routeService;

    @GetMapping("/route/{routeId}")
    public ResponseEntity<RouteResponse> findRouteWithMarkers(@PathVariable final Long routeId) {
        RouteResponse routeWithMarkers = routeService.findRouteWithMarkers(routeId);
        return ResponseEntity.ok().body(routeWithMarkers);
    }

    @GetMapping("/routes")
    public ResponseEntity<RoutesResponse> findAllRoutes() {
        RoutesResponse routesResponse = routeService.findAllRoutes();
        return ResponseEntity.ok().body(routesResponse);
    }

}
