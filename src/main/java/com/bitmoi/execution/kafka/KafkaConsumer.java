package com.bitmoi.execution.kafka;

import com.bitmoi.execution.domain.Execute;
import com.bitmoi.execution.domain.Order;
import com.bitmoi.execution.handler.OrderHandler;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Service
public class KafkaConsumer{
    private final Logger logger = LoggerFactory.getLogger(KafkaConsumer.class);

    @Autowired
    OrderHandler orderHandler;

    @KafkaListener(topics = "bitmoi-order", groupId = "bitmoi")
    public void consume(@Headers MessageHeaders headers, @Payload Object payload) {
        logger.info("CONSUME HEADERS : " + headers.toString());
        logger.info("CONSUME PAYLOAD : " + payload);
        System.out.println("CONSUME HEADERS : " + headers.toString());
        System.out.println("CONSUME PAYLOAD : " + payload);
    }


}