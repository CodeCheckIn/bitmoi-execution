package com.bitmoi.execution.service;

import com.bitmoi.execution.domain.Coin;
import com.bitmoi.execution.domain.Order;
import reactor.core.publisher.Mono;

public interface CoinService {

    Mono<Double> getCoinPriceById(Order order);
}
