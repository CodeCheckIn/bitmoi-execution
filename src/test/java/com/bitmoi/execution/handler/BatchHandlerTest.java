package com.bitmoi.execution.handler;

import com.bitmoi.execution.domain.Coin;
import com.bitmoi.execution.domain.Order;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

class BatchHandlerTest {

    BatchHandler batchHandler;

    Coin coin;

    @BeforeEach
    void setUp() {
        batchHandler = new BatchHandler();
        coin = new Coin(5,"XRP", 527.7);
    }

    @Test
    void 배치테스트() {
        batchHandler.getBatch(coin);
    }
}