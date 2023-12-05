package com.backend.remindmap.redis.controller;


import com.backend.remindmap.marker.application.MarkerService;
import com.backend.remindmap.marker.dto.request.MarkerLocationRequest;
import com.backend.remindmap.redis.domain.RankingDto;
import com.backend.remindmap.redis.dto.request.RankRequest;
import com.backend.remindmap.redis.dto.response.RankResponse;
import com.backend.remindmap.redis.service.RedisService;
import com.backend.remindmap.route.dto.response.RouteResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
public class RedisController {

    private final RedisService redisService;


    @GetMapping("/rank-marker")
    public List<RankResponse> getMarkerRankingList(@Valid @ModelAttribute RankRequest request) {
        return redisService.getMarkerRankingList(request);
    }

    @GetMapping("/rank-route")
    public List<RouteResponse> getRouteRankingList(@Valid @ModelAttribute RankRequest request) {
        return redisService.getRouteRankingList(request);
    }

}
