package com.backend.remindmap.marker.dto.request;

import com.backend.remindmap.group.domain.group.Group;
import com.backend.remindmap.marker.domain.Location;
import com.backend.remindmap.marker.domain.Marker;
import com.backend.remindmap.member.domain.Member.Member;
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

    private String imageUrl;

    private Location location;

    private boolean visiable;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime wentDate;

    public Marker toEntityByGroup(Member member, Group group, Point point, String imageUrl) {
        return Marker.builder()
                .member(member)
                .group(group)
                .title(title)
                .memo(memo)
                .imageUrl(imageUrl)
                .visiable(visiable)
                .location(location)
                .wentDate(wentDate)
                .point(point)
                .build();
    }
}
