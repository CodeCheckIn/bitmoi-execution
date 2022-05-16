package com.bitmoi.execution.service;

import com.bitmoi.execution.domain.Coin;
import com.bitmoi.execution.domain.Order;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.scheduler.Schedulers;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CoinServiceTest {
    @Autowired
    CoinService coinService;
    Coin coin;
    Order order;
    @BeforeEach
    void setUp() {
    }

    @Test
    void 코인정보가져오기(){
        coinService.getCoinPriceById(order)
                .subscribeOn(Schedulers.parallel())
                .log();
    }
}