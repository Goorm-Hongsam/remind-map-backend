package com.backend.remindmap.marker.dto.request;

import com.backend.remindmap.group.domain.Group;
import com.backend.remindmap.marker.domain.Location;
import com.backend.remindmap.marker.domain.Marker;
import com.backend.remindmap.member.domain.Member;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.locationtech.jts.geom.Point;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
@Getter
public class MarkerCreateRequest {

    @NotBlank(message = "공백일 수는 없습니다.")
    private String title;

    @NotBlank(message = "내용이 공백일 수 없습니다.")
    private String memo;

    private Location location;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime wentDate;

    private boolean visiable;

    public Marker toEntity(Member member, Point point) {
        return Marker.builder()
                .member(member)
                .title(title)
                .memo(memo)
                .visiable(visiable)
                .location(location)
                .wentDate(wentDate)
                .point(point)
                .build();
    }

    public Marker toEntityByGroup(Member member, Group group, Point point) {
        return Marker.markerWithGroupBuilder()
                .member(member)
                .group(group)
                .title(title)
                .memo(memo)
                .visiable(visiable)
                .location(location)
                .wentDate(wentDate)
                .point(point)
                .build();
    }
}
