package com.backend.remindmap.marker.domain;

import com.backend.remindmap.group.domain.group.Group;
import com.backend.remindmap.marker.exception.NoSuchMarkerException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MarkerRepository extends JpaRepository<Marker, Long> {

    default Marker getById(final Long id) {
        return findById(id)
                .orElseThrow(NoSuchMarkerException::new);
    }

    List<Marker> findByGroup(Group group);

    @Query("SELECT m FROM Marker m WHERE " +
            "m.location.latitude = :latitude AND " +
            "m.location.longitude = :longitude AND " +
            "m.visiable = :visible")
    Optional<Marker> findMarkerByLatitudeLongitudeAndVisibility(@Param("latitude") Double latitude,
                                                                @Param("longitude") Double longitude,
                                                                @Param("visible") boolean visible);

    @Query("SELECT m FROM Marker m WHERE " +
            "m.location.latitude = :latitude AND " +
            "m.location.longitude = :longitude AND " +
            "m.visiable = :visible AND " +
            "m.member.memberId = :memberId")
    Optional<Marker> findMarkerByLatitudeLongitudeVisibilityAndMemberId(@Param("latitude") Double latitude,
                                                                        @Param("longitude") Double longitude,
                                                                        @Param("visible") boolean visible,
                                                                        @Param("memberId") Long memberId);
}
