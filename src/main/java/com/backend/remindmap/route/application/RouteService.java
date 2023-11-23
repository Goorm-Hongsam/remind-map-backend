package com.backend.remindmap.route.application;

import com.backend.remindmap.group.domain.Group;
import com.backend.remindmap.group.repository.GroupRepository;
import com.backend.remindmap.marker.domain.Marker;
import com.backend.remindmap.marker.dto.response.MarkerResponse;
import com.backend.remindmap.markerRoute.application.MarkerRouteService;
import com.backend.remindmap.markerRoute.domain.MarkerRoute;
import com.backend.remindmap.route.domain.Route;
import com.backend.remindmap.route.domain.RouteRepository;
import com.backend.remindmap.route.dto.response.RouteResponse;
import com.backend.remindmap.route.dto.response.RoutesResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class RouteService {

    private final RouteRepository routeRepository;
    private final MarkerRouteService markerRouteService;
    private final GroupRepository groupRepository;

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
