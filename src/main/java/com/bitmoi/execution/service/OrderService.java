package com.bitmoi.execution.service;

import com.bitmoi.execution.domain.Coin;
import com.bitmoi.execution.domain.Order;
import org.springframework.data.relational.core.sql.In;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface OrderService {

    //받은 오더 주문 체결하기.
    Mono<Integer> executeOrder(Order order);

    Mono<Order> save(Order order);

    Flux<Order> findAllByCoinId(Coin coin);
}
