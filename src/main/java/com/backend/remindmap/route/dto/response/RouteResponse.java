package com.backend.remindmap.route.dto.response;

import com.backend.remindmap.marker.dto.response.MarkerResponse;
import com.backend.remindmap.route.domain.Route;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RouteResponse {
    private Long id;
    private String routeMaker;
    private String title;
    private LocalDate wentDate;
    private boolean visiable;
    private String memo;
    private String imageUrl;
    private List<MarkerResponse> markers;

    @Builder
    public RouteResponse(Long id, String routeMaker, String title, String memo, String imageUrl, boolean visiable, LocalDate wentDate, List<MarkerResponse> markers) {
        this.id = id;
        this.routeMaker = routeMaker;
        this.title = title;
        this.memo = memo;
        this.visiable = visiable;
        this.imageUrl = imageUrl;
        this.wentDate = wentDate;
        this.markers = markers;
    }

    public static RouteResponse fromEntity(Route route, List<MarkerResponse> markers) {
        return RouteResponse.builder()
                .id(route.getId())
                .routeMaker(route.getMember().getNickname())
                .title(route.getTitle())
                .wentDate(route.getWentDate())
                .memo(route.getMemo())
                .visiable(route.isVisiable())
                .imageUrl(route.getImageUrl())
                .markers(markers)
                .build();
    }

    public static RouteResponse fromEntityWithoutMarkers(Route route) {
        return RouteResponse.builder()
                .id(route.getId())
                .title(route.getTitle())
                .routeMaker(route.getMember().getNickname())
                .wentDate(route.getWentDate())
                .memo(route.getMemo())
                .visiable(route.isVisiable())
                .imageUrl(route.getImageUrl())
                .build();
    }

}
