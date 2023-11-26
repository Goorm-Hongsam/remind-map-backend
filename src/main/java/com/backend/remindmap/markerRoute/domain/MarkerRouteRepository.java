package com.backend.remindmap.markerRoute.domain;

import com.backend.remindmap.marker.domain.Marker;
import com.backend.remindmap.route.domain.Route;
import io.lettuce.core.ScanIterator;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MarkerRouteRepository extends JpaRepository<MarkerRoute, Long> {

    List<MarkerRoute> findByRoute(Route route);

    List<MarkerRoute> findByMarker(Marker marker);

    List<MarkerRoute> findByRouteAndMarkerVisiable(Route route, boolean visiable);

    List<MarkerRoute> findByMarkerAndRouteVisiable(Marker marker, boolean visiable);

    List<MarkerRoute> findByMarkerAndRouteVisiableAndRouteMemberMemberId(Marker marker, boolean visiable, Long memberId);
}

