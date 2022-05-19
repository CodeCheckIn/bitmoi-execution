package com.bitmoi.execution.kafka;

import com.bitmoi.execution.domain.Coin;
import com.bitmoi.execution.domain.Execute;
import com.bitmoi.execution.domain.Order;
import com.bitmoi.execution.handler.BatchHandler;
import com.bitmoi.execution.handler.OrderHandler;
import com.netflix.discovery.converters.Auto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import reactor.core.Disposable;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class KafkaConsumerService {
    private final Logger logger = LoggerFactory.getLogger(KafkaConsumerService.class);

    @Autowired
    BatchHandler batchHandler;

    @Autowired
    OrderHandler orderHandler;

    @KafkaListener(topics = "bitmoi-quotation", containerFactory = "coinConcurrentKafkaListenerContainerFactory")
    public Disposable consume(Coin coin) {
        return batchHandler.getBatch(coin);
    }

    @KafkaListener(topics = "bitmoi-order", containerFactory = "orderConcurrentKafkaListenerContainerFactory")
    public Disposable consume(Order order) {
        return orderHandler.getOrder(order);
//        System.out.printf("[order] '%s %s %s \n", order.getCoinid(), order.getPrice(), order.getQuantity());
    }
}