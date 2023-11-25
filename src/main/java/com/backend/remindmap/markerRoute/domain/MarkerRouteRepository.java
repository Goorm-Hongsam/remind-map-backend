package com.backend.remindmap.markerRoute.domain;

import com.backend.remindmap.marker.domain.Marker;
import com.backend.remindmap.route.domain.Route;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MarkerRouteRepository extends JpaRepository<MarkerRoute, Long> {

    List<MarkerRoute> findByRoute(Route route);

    List<MarkerRoute> findByMarker(Marker marker);
}

