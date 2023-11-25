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
    private String imageUrl;
    private Location location;
    private LocalDateTime wentDate;

    @Builder
    public MarkerResponse(Long id, String nickName, String title, String memo, String imageUrl, Location location, LocalDateTime wentDate) {
        this.id = id;
        this.nickName = nickName;
        this.title = title;
        this.memo = memo;
        this.imageUrl = imageUrl;
        this.location = location;
        this.wentDate = wentDate;
    }

    public static MarkerResponse fromEntity(Marker marker) {
        return MarkerResponse.builder()
                .id(marker.getId())
                .nickName(marker.getMember().getNickname())
                .title(marker.getTitle())
                .memo(marker.getMemo())
                .imageUrl(marker.getImageUrl())
                .wentDate(marker.getWentDate())
                .location(marker.getLocation())
                .build();
    }
}
