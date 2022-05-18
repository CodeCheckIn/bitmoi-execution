package com.bitmoi.execution.kafka;

import com.bitmoi.execution.domain.Coin;
import com.bitmoi.execution.domain.Order;
import com.bitmoi.execution.handler.BatchHandler;
import com.bitmoi.execution.handler.OrderHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumer{
    private final Logger logger = LoggerFactory.getLogger(KafkaConsumer.class);
    @Autowired
    BatchHandler batchHandler;
    @KafkaListener(topics = "bitmoi-quotation", containerFactory = "coinConcurrentKafkaListenerContainerFactory")
    public void consume(Coin coin) {
        batchHandler.getBatch(coin);
        System.out.printf("[quotation] '%s %s %s \n", coin.getCoinId(), coin.getName(), coin.getPrice());
    }
    @KafkaListener(topics = "bitmoi-order", containerFactory = "orderConcurrentKafkaListenerContainerFactory")
    public void consume(Order order) {
        System.out.printf("[order] '%s %s %s \n", order.getCoinid(), order.getPrice(), order.getQuantity());
    }
}