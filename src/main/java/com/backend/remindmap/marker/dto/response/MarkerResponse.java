package com.backend.remindmap.marker.dto.response;

import com.backend.remindmap.marker.domain.Location;
import com.backend.remindmap.marker.domain.Marker;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MarkerResponse {

    private Long id;
    private String nickName;
    private String title;
    private String memo;
    private Location location;
    private int view;
    private boolean visiable;
    private LocalDateTime wentDate;

    @Builder
    public MarkerResponse(Long id, String nickName, String title, String memo, Location location, int view, boolean visiable, LocalDateTime wentDate) {
        this.id = id;
        this.nickName = nickName;
        this.title = title;
        this.memo = memo;
        this.location = location;
        this.view = view;
        this.visiable = visiable;
        this.wentDate = wentDate;
    }

    public static MarkerResponse fromEntity(Marker marker) {
        return MarkerResponse.builder()
                .id(marker.getId())
                .nickName(marker.getMember().getNickname())
                .title(marker.getTitle())
                .memo(marker.getMemo())
                .location(marker.getLocation())
                .view(marker.getView())
                .visiable(marker.isVisiable())
                .wentDate(marker.getWentDate())
                .build();
    }
}
