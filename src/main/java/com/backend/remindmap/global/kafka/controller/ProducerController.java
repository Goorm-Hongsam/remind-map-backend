package com.backend.remindmap.global.kafka.controller;

import com.backend.remindmap.global.utils.MapperUtil;
import com.backend.remindmap.marker.dto.request.MarkerRankRequest;
import com.backend.remindmap.route.dto.request.RouteRankRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("kafka")
@AllArgsConstructor
@Slf4j
public class ProducerController {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final NewTopic markerTopic;
    private final NewTopic routeTopic;

    @GetMapping("/publish/markerTopic/test")
    public String publishMarkerTopic() {

        Long markerId = 1L;
        MarkerRankRequest request = MarkerRankRequest.from(markerId);
        String message = MapperUtil.convertMarkerRankToJson(request);

        ListenableFuture<SendResult<String, String>> future = kafkaTemplate.send(markerTopic.name(), message);
        future.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {
            @Override
            public void onFailure(Throwable ex) {
                log.info(message + "메시지 전송 실패");
            }

            @Override
            public void onSuccess(SendResult<String, String> result) {
                log.info("마커 - [{}] 메시지 전송 성공", message);
                log.info("{} offset으로 메시지 전달", String.valueOf(result.getRecordMetadata().offset()));
            }
        });
        return "done";
    }

    @GetMapping("/publish/routeTopic/test")
    public String publishRouteTopic() {

        Long routeId = 1L;
        RouteRankRequest request = RouteRankRequest.from(routeId);
        String message = MapperUtil.convertRouteRankToJson(request);

        ListenableFuture<SendResult<String, String>> future = kafkaTemplate.send(routeTopic.name(), message);
        future.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {
            @Override
            public void onFailure(Throwable ex) {
                log.info(message + "메시지 전송 실패");
            }

            @Override
            public void onSuccess(SendResult<String, String> result) {
                log.info("루트 - [{}] 메시지 전송 성공", message);
                log.info("{} offset으로 메시지 전달", String.valueOf(result.getRecordMetadata().offset()));
            }
        });
        return "done";
    }

}
