package com.backend.remindmap.marker.domain;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class Location {

    @NotNull
    private Double latitude;

    @NotNull
    private Double longitude;
}
