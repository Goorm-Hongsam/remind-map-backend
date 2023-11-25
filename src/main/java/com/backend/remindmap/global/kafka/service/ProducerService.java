package com.backend.remindmap.global.kafka.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProducerService {

    public static final String SUCCESS_LOG_MESSAGE_FORMAT = "[{}] {}";
    public static final String SUCCESS_LOG_EXPLAIN_MESSAGE = "{} partition 내 {} offset으로 메시지 전달";
    public static final String FAIL_LOG_MESSAGE_FORMAT = "[{}] {}";
    private static final String MESSAGE_SEND_FAILED = "메시지 전송 실패";
    private static final String MESSAGE_SEND_SUCCESS = "메시지 전송 성공";

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final NewTopic markerTopic;

    /**
     * Future 클래스를 상속받음으로써, Sync (동기) 전송이 아닌, Async (비동기) 전송
     * @param message: marker topic 으로 전달할 String 형태의 메시지
     */
    public void sendMessageToKafka(String message) {
        ListenableFuture<SendResult<String, String>> future = kafkaTemplate.send(markerTopic.name(), message);
        future.addCallback(
                result -> logSuccess(message, result),
                ex -> logFailure(message, ex)
        );
    }

    private void logSuccess(String message, SendResult<String, String> result) {
        log.info(SUCCESS_LOG_MESSAGE_FORMAT, MESSAGE_SEND_SUCCESS, message);
        log.info(SUCCESS_LOG_EXPLAIN_MESSAGE, result.getRecordMetadata().partition(), result.getRecordMetadata().offset());
    }

    private void logFailure(String message, Throwable ex) {
        log.error(FAIL_LOG_MESSAGE_FORMAT, MESSAGE_SEND_FAILED, message, ex);
    }
}
