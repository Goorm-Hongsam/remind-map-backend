package com.backend.remindmap.marker.dto.request;


import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Getter
public class MarkerUpdateRequest {

    @NotBlank(message = "제목은 비어있을 수 없습니다.")
    private String title;

    @Size(max = 500, message = "내부 메모는 비어있을 수 없습니다.")
    private String memo;

    private boolean visible;

    @NotNull(message = "다녀간 날짜는 비어있을 수 없습니다.")
    private LocalDateTime wentDate;
}
