package com.backend.remindmap.global.kafka.service;

import com.backend.remindmap.redis.service.RedisService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class ConsumerService {

    private final RedisService redisService;

    @KafkaListener(topics = "#{markerTopic.name}", groupId = "group1")
    public void consumeMarkerTopic(@Payload String message, @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition) {
        Map<Object, Object> map = new HashMap<>();
        ObjectMapper mapper = new ObjectMapper();

        try {
            map = mapper.readValue(message, new TypeReference<>() {});
        } catch (JsonProcessingException ex) {
            ex.printStackTrace();
        }
        String markerId = (String) map.get("typeId");
        String type = (String) map.get("type");
        log.info(markerId + " " + type);

        redisService.addScore(type, markerId);
        log.info("consume message: {} from partition: {}", message, partition);
    }
}
