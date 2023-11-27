package com.backend.remindmap.marker.dto.request;

import com.backend.remindmap.group.domain.group.Group;
import com.backend.remindmap.marker.domain.Marker;
import com.backend.remindmap.markerRoute.domain.MarkerRoute;
import com.backend.remindmap.member.domain.Member.Member;
import com.backend.remindmap.route.domain.Route;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MarkerUpdateRequest {

    private Long markerId;

    @NotBlank(message = "제목이 공백일 수 없습니다.")
    private String title;

    @NotBlank(message = "내용이 공백일 수 없습니다.")
    private String memo;

    private boolean visiable;

    private LocalDate wentDate;

    @Builder
    public MarkerUpdateRequest(Long markerId, String title, String memo, boolean visiable, LocalDate wentDate) {
        this.markerId = markerId;
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

    public Route toRoute(Member member, String imageUrl, Group group) {
        return Route.builder()
                .member(member)
                .group(group)
                .title(title)
                .memo(memo)
                .imageUrl(imageUrl)
                .visiable(visiable)
                .view(0)
                .wentDate(wentDate)
                .build();
    }
}
