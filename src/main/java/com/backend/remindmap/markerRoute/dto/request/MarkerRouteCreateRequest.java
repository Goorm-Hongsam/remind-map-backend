package com.backend.remindmap.markerRoute.dto.request;

import com.backend.remindmap.marker.domain.Marker;
import com.backend.remindmap.markerRoute.domain.MarkerRoute;
import com.backend.remindmap.member.domain.Member;
import com.backend.remindmap.route.domain.Route;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MarkerRouteCreateRequest {

    @NotNull(message = "markerId 목록이 null일 수 없습니다.")
    private List<Long> markerIds;

    @NotBlank(message = "제목이 공백일 수 없습니다.")
    private String title;

    @NotBlank(message = "내용이 공백일 수 없습니다.")
    private String memo;

    private boolean visiable;

    private LocalDateTime wentDate;

    @Builder
    public MarkerRouteCreateRequest(List<Long> markerIds, String title, String memo, boolean visiable, LocalDateTime wentDate) {
        this.markerIds = markerIds;
        this.title = title;
        this.memo = memo;
        this.visiable = visiable;
        this.wentDate = wentDate;
    }

    public MarkerRoute toMarkerRoute(Marker marker, Route route) {
        return MarkerRoute.builder()
                .marker(marker)
                .route(route)
                .build();
    }

    public Route toRoute(Member member) {
        return Route.builder()
                .member(member)
                .title(title)
                .memo(memo)
                .visiable(visiable)
                .view(0)
                .wentDate(wentDate)
                .build();
    }
}
