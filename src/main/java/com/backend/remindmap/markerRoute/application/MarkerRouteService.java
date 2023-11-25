package com.backend.remindmap.markerRoute.application;

import com.backend.remindmap.marker.domain.Marker;
import com.backend.remindmap.marker.domain.MarkerRepository;
import com.backend.remindmap.marker.dto.request.MarkerLocationRequest;
import com.backend.remindmap.marker.dto.response.MarkerResponse;
import com.backend.remindmap.marker.exception.NoSuchMarkerException;
import com.backend.remindmap.markerRoute.domain.MarkerRoute;
import com.backend.remindmap.markerRoute.domain.MarkerRouteRepository;
import com.backend.remindmap.markerRoute.dto.request.MarkerRouteCreateRequest;
import com.backend.remindmap.markerRoute.dto.response.IntegrativeMarkerRouteCreateResponse;
import com.backend.remindmap.markerRoute.dto.response.MarkerRouteCreateResponse;
import com.backend.remindmap.member.domain.Member.Member;
import com.backend.remindmap.member.repository.MemberRepository;
import com.backend.remindmap.route.domain.Route;
import com.backend.remindmap.route.domain.RouteRepository;
import com.backend.remindmap.route.dto.response.RouteResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class MarkerRouteService {

    private final MarkerRepository markerRepository;
    private final MemberRepository memberRepository;
    private final RouteRepository routeRepository;
    private final MarkerRouteRepository markerRouteRepository;


    @Transactional
    public IntegrativeMarkerRouteCreateResponse save(final Long memberId, final MarkerRouteCreateRequest request, String imageUrl) {
        Member member = memberRepository.findMemberById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));

        List<Long> markerIds = request.getMarkerIds();
        List<MarkerRouteCreateResponse> responses = new ArrayList<>();

        Route route = request.toRoute(member, imageUrl);
        routeRepository.save(route);

        List<Marker> markers = markerRepository.findAllById(markerIds);
        markerIds.forEach(id -> {
            if (markers.stream().noneMatch(marker -> marker.getId().equals(id))) {
                throw new NoSuchMarkerException();
            }
        });

        List<MarkerRoute> markerRoutes = markers.stream()
                .map(marker -> request.toMarkerRoute(marker, route))
                .collect(Collectors.toList());
        markerRouteRepository.saveAll(markerRoutes);

        Long createdRouteId = route.getId();
        markerRoutes.forEach(markerRoute -> responses.add(new MarkerRouteCreateResponse(markerRoute.getId())));

        return new IntegrativeMarkerRouteCreateResponse(createdRouteId, responses);
    }

    public List<MarkerResponse> findMarkersByRoute(Route route) {
        List<MarkerRoute> markerRoutes = markerRouteRepository.findByRoute(route);
        return markerRoutes.stream()
                .map(MarkerRoute::getMarker)
                .map(Marker::toResponse)
                .collect(Collectors.toList());
    }

    public List<RouteResponse> findAllByMarkerLocation(MarkerLocationRequest request) {

        Optional<Marker> markerOptional = markerRepository.findByLocationLatitudeAndLocationLongitude(request.getLatitude(), request.getLongitude());
        if (!markerOptional.isPresent()) {
            return new ArrayList<>();
        }

        Marker marker = markerOptional.get();
        List<MarkerRoute> markerRoutes = markerRouteRepository.findByMarker(marker);

        List<RouteResponse> routesWithMarkers = new ArrayList<>();
        for (MarkerRoute markerRoute : markerRoutes) {
            Route route = markerRoute.getRoute();
            List<Marker> markersInRoute = markerRouteRepository.findByRoute(route).stream()
                    .map(MarkerRoute::getMarker)
                    .collect(Collectors.toList());

            List<MarkerResponse> markerResponses = markersInRoute.stream()
                    .map(MarkerResponse::fromEntity)
                    .collect(Collectors.toList());

            RouteResponse routeResponse = RouteResponse.fromEntity(route, markerResponses);
            routesWithMarkers.add(routeResponse);
        }

        return routesWithMarkers;

    }
}
