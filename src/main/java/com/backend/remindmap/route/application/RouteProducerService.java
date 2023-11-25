package com.backend.remindmap.route.application;

import com.backend.remindmap.global.kafka.service.ProducerService;
import com.backend.remindmap.global.utils.MapperUtil;
import com.backend.remindmap.route.dto.request.RouteRankRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class RouteProducerService {

    private final ProducerService producerService;

    public void send(RouteRankRequest request) {
        String message = MapperUtil.convertRouteRankToJson(request);
        producerService.sendMessageToKafka(message);
    }
}
