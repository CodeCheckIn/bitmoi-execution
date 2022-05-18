package com.bitmoi.execution.kafka;

import com.bitmoi.execution.domain.Coin;
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
    public static final String BITMOI_ORDER = "bitmoi-order";
    public static final String BITMOI_QUOTATION = "bitmoi-quotation";
    public static final String BITMOI = "bitmoi";
    private final Logger logger = LoggerFactory.getLogger(KafkaConsumer.class);

    @Autowired
    OrderHandler orderHandler;

    @KafkaListener(topics = BITMOI_ORDER, groupId = BITMOI)
    public void consumeOrder(Order order) {
        System.out.printf("[Order] '%s %s %s \n", order.getCoinid(), order.getPrice(), order.getQuantity());
    }

    @KafkaListener(topics = BITMOI_QUOTATION, groupId = BITMOI)
    public void consumeBatch(Coin coin) {
        System.out.printf("[Coin] '%s %s %s \n", coin.getCoinId(), coin.getName(), coin.getPrice());
    }
}