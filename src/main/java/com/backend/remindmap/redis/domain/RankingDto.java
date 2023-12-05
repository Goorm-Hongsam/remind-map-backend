package com.backend.remindmap.redis.domain;

import lombok.Data;

@Data
public class RankingDto{

    private String boardId;
    private Double score;

    public RankingDto(String boardId, Double score) {
        this.boardId = boardId;
        this.score = score;
    }

}
