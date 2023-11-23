package com.backend.remindmap.redis.controller;


import com.backend.remindmap.marker.application.MarkerService;
import com.backend.remindmap.marker.dto.request.MarkerLocationRequest;
import com.backend.remindmap.redis.domain.RankingDto;
import com.backend.remindmap.redis.dto.request.RankRequest;
import com.backend.remindmap.redis.dto.response.RankResponse;
import com.backend.remindmap.redis.service.RedisService;
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


    @GetMapping("/rank")
    public List<RankResponse> getRankingList(@Valid @ModelAttribute RankRequest request) {
        return redisService.getRankingList(request);
    }

}
