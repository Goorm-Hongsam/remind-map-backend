package com.backend.remindmap.route.application;

import com.backend.remindmap.marker.dto.response.MarkerResponse;
import com.backend.remindmap.markerRoute.application.MarkerRouteService;
import com.backend.remindmap.route.domain.Route;
import com.backend.remindmap.route.domain.RouteRepository;
import com.backend.remindmap.route.dto.response.RouteResponse;
import com.backend.remindmap.route.dto.response.RoutesResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class RouteService {

    private final RouteRepository routeRepository;
    private final MarkerRouteService markerRouteService;

    public RouteResponse findRouteWithMarkers(Long routeId) {
        Route route = routeRepository.getById(routeId);
        List<MarkerResponse> markers = markerRouteService.findMarkersByRoute(route);

        return RouteResponse.fromEntity(route, markers);
    }

    public RoutesResponse findAllRoutes() {
        List<Route> routes = routeRepository.findAll();

        return RoutesResponse.fromEntity(routes);
    }

}
