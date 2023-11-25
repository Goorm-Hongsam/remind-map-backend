package com.backend.remindmap.global.utils;

import com.backend.remindmap.marker.dto.request.MarkerRankRequest;
import com.backend.remindmap.marker.exception.NotConvertJsonException;
import com.backend.remindmap.route.dto.request.RouteRankRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class MapperUtil {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static String convertMarkerRankToJson(MarkerRankRequest request) {
        try {
            return objectMapper.writeValueAsString(request);
        } catch (JsonProcessingException ex) {
            throw new NotConvertJsonException();
        }
    }

    public static String convertRouteRankToJson(RouteRankRequest request) {
        try {
            return objectMapper.writeValueAsString(request);
        } catch (JsonProcessingException ex) {
            throw new NotConvertJsonException();
        }
    }

}
