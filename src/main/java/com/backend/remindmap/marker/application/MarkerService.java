package com.backend.remindmap.marker.application;

import com.backend.remindmap.global.utils.GeometryUtil;
import com.backend.remindmap.group.domain.group.Group;
import com.backend.remindmap.group.repository.group.GroupRepository;
import com.backend.remindmap.marker.domain.Direction;
import com.backend.remindmap.marker.domain.Location;
import com.backend.remindmap.marker.domain.Marker;
import com.backend.remindmap.marker.domain.MarkerRepository;
import com.backend.remindmap.marker.dto.request.MarkerCreateRequest;
import com.backend.remindmap.marker.dto.request.MarkerLocationRequest;
import com.backend.remindmap.marker.dto.request.MarkerUpdateRequest;
import com.backend.remindmap.marker.dto.response.MarkerResponse;
import com.backend.remindmap.marker.exception.NotOwnerMarkerException;
import com.backend.remindmap.member.domain.Member.Member;
import com.backend.remindmap.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.io.ParseException;
import org.locationtech.jts.io.WKTReader;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class MarkerService {

    private final MarkerRepository markerRepository;
    private final MemberRepository memberRepository;
    private final GroupRepository groupRepository;
    private final EntityManager em;

    private static final Double SEARCH_MAX_DISTANCE = 30.0;

    public List<MarkerResponse> findMarkersByLocation(Long memberId, MarkerLocationRequest request) {
        Location northEast = GeometryUtil.calculate(request.getLatitude(), request.getLongitude(), SEARCH_MAX_DISTANCE, Direction.NORTHEAST.getBearing());
        Location southWest = GeometryUtil.calculate(request.getLatitude(), request.getLongitude(), SEARCH_MAX_DISTANCE, Direction.SOUTHWEST.getBearing());

        if (!memberId.equals(null)) {
            List<Marker> markers = searchMarkersWithinBounds(northEast, southWest, memberId);
            return convertMarkersToDTOs(markers);
        }

        List<Marker> markers = searchMarkersWithinBounds(northEast, southWest);
        return convertMarkersToDTOs(markers);
    }

    public List<Marker> searchMarkersWithinBounds(Location northEast, Location southWest, Long memberId) {
        String pointFormat = String.format(
                "'LINESTRING(%f %f, %f %f)'",
                northEast.getLatitude(), northEast.getLongitude(), southWest.getLatitude(), southWest.getLongitude()
        );

        Query query = em.createNativeQuery(
                        "SELECT * FROM markers AS m " +
                        "WHERE MBRContains(ST_LINESTRINGFROMTEXT(" + pointFormat + "), m.point)" +
                        "AND (m.visiable = true OR m.member_id = :memberId)",
                Marker.class
        ).setMaxResults(10);
        query.setParameter("memberId", memberId);

        return query.getResultList();
    }

    public List<Marker> searchMarkersWithinBounds(Location northEast, Location southWest) {
        String pointFormat = String.format(
                "'LINESTRING(%f %f, %f %f)'",
                northEast.getLatitude(), northEast.getLongitude(), southWest.getLatitude(), southWest.getLongitude()
        );

        Query query = em.createNativeQuery(
                        "SELECT * FROM markers AS m " +
                        "WHERE MBRContains(ST_LINESTRINGFROMTEXT(" + pointFormat + "), m.point)" +
                        "AND m.visiable = true",
                Marker.class
        ).setMaxResults(10);

        return query.getResultList();
    }

    public MarkerResponse findMarker(final Long markerId) {
        Marker marker = markerRepository.getById(markerId);

        return marker.toResponse();
    }

    public List<MarkerResponse> convertMarkersToDTOs(List<Marker> markers) {
        return markers.stream()
                .map(MarkerResponse::fromEntity)
                .collect(Collectors.toList());
    }

    public Point convertRequestToPoint(MarkerCreateRequest request) throws ParseException {
        return convertLocationToPoint(request.getLocation());
    }

    public Point convertLocationToPoint(Location location) throws ParseException {
        if (!location.equals(null)) {
            return createPointFromCoordinates(location.getLatitude(), location.getLongitude());
        }
        return null;
    }

    public Point createPointFromCoordinates(Double latitude, Double longitude) throws org.locationtech.jts.io.ParseException {
        String pointWKT = String.format("POINT(%s %s)", latitude, longitude);
        return (Point) new WKTReader().read(pointWKT);
    }

    @Transactional
    public void delete(Long groupId, Long id) {
        Group group = groupRepository.findGroupById(groupId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 그룹입니다."));

        Marker marker = markerRepository.getById(id);

        markerRepository.delete(marker);
    }

    @Transactional
    public MarkerResponse saveByGroup(Long memberId, Long groupId, MarkerCreateRequest request, String imageUrl) throws ParseException {
        Member member = memberRepository.findMemberById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));

        Group group = groupRepository.findGroupById(groupId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 그룹입니다."));

        Point point = convertRequestToPoint(request);
        Marker marker = request.toEntityByGroup(member, group, point, imageUrl);

        Marker savedMarker = markerRepository.save(marker);
        return MarkerResponse.fromEntity(savedMarker);

    }

    public List<MarkerResponse> findMarkersByGroup(Long groupId) {
        Group group = groupRepository.findGroupById(groupId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 그룹입니다."));

        List<Marker> markersInGroup = markerRepository.findByGroup(group);

        return markersInGroup.stream()
                .map(MarkerResponse::fromEntity)
                .collect(Collectors.toList());
    }

    public MarkerResponse updateMarker(Long groupId, MarkerUpdateRequest request, String imgUrl) {
        groupRepository.findGroupById(groupId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 그룹입니다."));

        Marker marker = markerRepository.getById(request.getMarkerId());

        marker.updateWith(request, imgUrl);

        Marker savedMarker = markerRepository.save(marker);
        return MarkerResponse.fromEntity(savedMarker);
    }

    public void validateMarkerOwner(Long memberId, Long markerId) {
        Marker marker = markerRepository.getById(markerId);
        if (memberId != marker.getId()) {
            throw new NotOwnerMarkerException();
        }
    }

}
