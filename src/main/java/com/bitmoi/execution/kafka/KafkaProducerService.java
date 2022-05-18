package com.bitmoi.execution.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducerService {

    public static final String BITMOI_EXECUTE = "bitmoi-execute";

    @Autowired
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public KafkaProducerService(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    //주문 관련된 메시지
    public void sendExecuteMessage(Object data) {
        Message<Object> executeMessage = MessageBuilder
                .withPayload(data)
                .setHeader(KafkaHeaders.TOPIC, BITMOI_EXECUTE)
                .build();
        kafkaTemplate.send(executeMessage);
    }
}
