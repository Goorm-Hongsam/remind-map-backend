package com.backend.remindmap.markerRoute.dto.response;

import com.backend.remindmap.marker.domain.Marker;
import com.backend.remindmap.route.domain.Route;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class MarkerRouteResponse {

    private Route route;
    private List<Marker> markers;

}