package com.backend.remindmap.route.domain;

import com.backend.remindmap.route.exception.NoSuchRouteException;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RouteRepository extends JpaRepository<Route, Long> {

    default Route getById(final Long id) {
        return findById(id)
                .orElseThrow(NoSuchRouteException::new);
    }
}
