package com.backend.remindmap.redis.service;


import com.backend.remindmap.redis.domain.RankingDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class RedisService {

    private final RedisTemplate redisTemplate;

    /**
     * @param type : marker or route, type 은 랭킹 집계를 두 곳에서 하기 때문에 각 테이블 명을 의미합니다. (마커 랭킹 테이블, 루트 랭킹 테이블)
     * @param id : 게시물 ID를 의미합니다.
     */
    public void addScore(String type, String id) {
        redisTemplate.opsForZSet().incrementScore(type, id, 1);
    }

    /**
     * @param type : marker or route
     * @param count : 상위 몇 번째 까지 리스트로 담아서 보내줄지 정하는 파라미터입니다.
     */
    public List<RankingDto> getRankingList(String type, int count) {
        ZSetOperations<String, String> zSetOperations = redisTemplate.opsForZSet();
        Set<ZSetOperations.TypedTuple<String>> ranking = zSetOperations.reverseRangeWithScores(type, 0, count - 1);
        return ranking.stream()
                .map(tuple -> new RankingDto(tuple.getValue(), tuple.getScore()))
                .collect(Collectors.toList());
    }

}