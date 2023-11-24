package com.backend.remindmap.redis.service;


import com.backend.remindmap.marker.domain.Marker;
import com.backend.remindmap.marker.domain.MarkerRepository;
import com.backend.remindmap.redis.domain.RankingDto;
import com.backend.remindmap.redis.dto.request.RankRequest;
import com.backend.remindmap.redis.dto.response.RankResponse;
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
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class RedisService {

    public static final int DEFAULT_RANK_COUNT = 5;

    private final RedisTemplate redisTemplate;
    private final MarkerRepository markerRepository;

    /**
     * @param type : marker or route, type 은 랭킹 집계를 두 곳에서 하기 때문에 각 테이블 명을 의미합니다. (마커 랭킹 테이블, 루트 랭킹 테이블)
     * @param id : 게시물 ID를 의미합니다.
     */
    @Transactional
    public void addScore(String type, String id) {
        redisTemplate.opsForZSet().incrementScore(type, id, 1);
    }

    /** @param request
       * type : marker or route
       * count : 상위 몇 번째 까지 리스트로 담아서 보내줄지 정하는 파라미터입니다.     *
     * @return
     */
    public List<RankResponse> getRankingList(RankRequest request) {
        int count = determineCount(request);
        ZSetOperations<String, String> zSetOperations = redisTemplate.opsForZSet();

        Set<ZSetOperations.TypedTuple<String>> ranking = zSetOperations.reverseRangeWithScores(request.getBoardType(), 0, count - 1);
        return ranking.stream()
                .map(tuple -> markerRepository.getById(Long.parseLong(tuple.getValue())))
                .map(RankResponse::fromMarker)
                .collect(Collectors.toList());
    }

    private static int determineCount(RankRequest request) {
        int count = DEFAULT_RANK_COUNT;
        if (request.getCount() != null) {
            count = request.getCount();
        }
        return count;
    }

}