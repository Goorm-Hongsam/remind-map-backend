package com.backend.remindmap.global.utils;

import com.backend.remindmap.marker.dto.request.MarkerRankRequest;
import com.backend.remindmap.marker.exception.NotConvertJsonException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class MarkerMapperUtil {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static String convertToJson(MarkerRankRequest request) {
        try {
            return objectMapper.writeValueAsString(request);
        } catch (JsonProcessingException ex) {
            throw new NotConvertJsonException();
        }
    }

}
