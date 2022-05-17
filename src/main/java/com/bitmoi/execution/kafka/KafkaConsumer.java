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
<<<<<<< HEAD
//    @KafkaListener(topics = "order-bid", groupId = "bitmoi")
//    public void consumeBid(String message) {
//        logger.info(String.format("Consumed Bid message -> %s", message));
//    }
//
//    @KafkaListener(topics = "order-ask", groupId = "bitmoi")
//    public void consumeAsk(String message) {
//        logger.info(String.format("Consumed Ask message -> %s", message));
//    }
//    @KafkaListener(topics = "order", groupId = "bitmoi")
//    public Mono<Execute> consumeOrder(String message) {
//        logger.info(String.format("Consumed Ask message -> %s", message));
//        ObjectMapper mapper = new ObjectMapper();
//        try {
//            Order order = mapper.readValue(message, Order.class);
//            return orderHandler.getOrder(order);
//        } catch (JsonProcessingException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
=======

    @KafkaListener(topics = "bitmoi-order", groupId = "bitmoi")
    public void consume(@Headers MessageHeaders headers, @Payload Object payload) {
        logger.info("CONSUME HEADERS : " + headers.toString());
        logger.info("CONSUME PAYLOAD : " + payload);
        System.out.println("CONSUME HEADERS : " + headers.toString());
        System.out.println("CONSUME PAYLOAD : " + payload);
    }


>>>>>>> 04c267b7ccf5b0fb2c7beefb9c6f5babaaae76fc
}