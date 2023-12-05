package com.backend.remindmap.redis.dto.response;

import com.backend.remindmap.marker.domain.Marker;
import com.backend.remindmap.redis.dto.request.RankRequest;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RankResponse {

    private Long id;
    private String title;
    private String memo;
    private String nickName;
    private LocalDateTime wentDate;

    @Builder
    private RankResponse(Long id, String title, String memo, String nickName, LocalDateTime wentDate) {
        this.id = id;
        this.title = title;
        this.memo = memo;
        this.nickName = nickName;
        this.wentDate = wentDate;
    }

    public static RankResponse fromMarker(Marker marker) {
        return RankResponse.builder()
                .id(marker.getId())
                .title(marker.getTitle())
                .memo(marker.getMemo())
                .nickName(marker.getMember().getNickname())
                .wentDate(marker.getWentDate())
                .build();
    }
}
