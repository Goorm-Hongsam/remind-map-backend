package com.backend.remindmap.markerRoute.domain;

import com.backend.remindmap.marker.domain.Marker;
import com.backend.remindmap.route.domain.Route;
import io.lettuce.core.ScanIterator;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MarkerRouteRepository extends JpaRepository<MarkerRoute, Long> {

    List<MarkerRoute> findByRoute(Route route);

    List<MarkerRoute> findByMarker(Marker marker);

    List<MarkerRoute> findByRouteAndMarkerVisiable(Route route, boolean visiable);

    List<MarkerRoute> findByMarkerAndRouteVisiable(Marker marker, boolean visiable);

    @Query("SELECT mr FROM MarkerRoute mr WHERE " +
            "mr.marker.visiable = true AND " +
            "(mr.route.visiable = true OR mr.route.member.memberId = :memberId) AND " +
            "mr.marker = :marker")
    List<MarkerRoute> findMarkerRoutesByVisibilityAndMemberId(@Param("marker") Marker marker, @Param("memberId") Long memberId);
}

