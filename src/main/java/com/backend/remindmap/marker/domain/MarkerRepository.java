package com.backend.remindmap.marker.domain;

import com.backend.remindmap.marker.exception.NoSuchMarkerException;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MarkerRepository extends JpaRepository<Marker, Long> {

    default Marker getById(final Long id) {
        return findById(id)
                .orElseThrow(NoSuchMarkerException::new);
    }

    Optional<Marker> findByLocationLatitudeAndLocationLongitude(double latitude, double longitude);
}
