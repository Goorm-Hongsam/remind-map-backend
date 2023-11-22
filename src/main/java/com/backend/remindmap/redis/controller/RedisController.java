package com.backend.remindmap.redis.controller;


import com.backend.remindmap.redis.domain.RankingDto;
import com.backend.remindmap.redis.service.RedisService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
public class RedisController {

    private final RedisService redisService;

    @GetMapping("/rank/{boardType}")
    public List<RankingDto> getRankingList(@PathVariable String boardType, @RequestBody Integer count) {
        return redisService.getRankingList(boardType, count);
    }

}
