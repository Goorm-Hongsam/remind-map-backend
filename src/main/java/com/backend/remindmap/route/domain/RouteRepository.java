package com.backend.remindmap.route.domain;

import com.backend.remindmap.group.domain.group.Group;
import com.backend.remindmap.route.exception.NoSuchRouteException;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RouteRepository extends JpaRepository<Route, Long> {

    default Route getById(final Long id) {
        return findById(id)
                .orElseThrow(NoSuchRouteException::new);
    }

    List<Route> findByGroup(final Group group);
}
