package com.backend.remindmap.marker.application;

import com.backend.remindmap.global.kafka.service.ProducerService;
import com.backend.remindmap.global.utils.MapperUtil;
import com.backend.remindmap.marker.dto.request.MarkerRankRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class MarkerProducerService {

    private final ProducerService producerService;

    public void send(MarkerRankRequest request) {
        String message = MapperUtil.convertMarkerRankToJson(request);
        producerService.sendMessageToKafka(message);
    }

}
