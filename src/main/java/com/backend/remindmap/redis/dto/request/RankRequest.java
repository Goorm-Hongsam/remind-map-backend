package com.backend.remindmap.redis.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@Getter
public class RankRequest {

    @NotNull(message = "공백일 수는 없습니다.")
    private Integer count;
}
