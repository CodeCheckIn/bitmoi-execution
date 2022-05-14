package com.bitmoi.execution.repository;

import com.bitmoi.execution.domain.Order;
import org.springframework.data.r2dbc.repository.Modifying;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface OrderRepository extends ReactiveCrudRepository<Order, Integer> {

    @Modifying
    @Query("UPDATE orderbook set state='execute' where state='wait' and coinid=:coinid")
    Mono<Integer> updateByIsExecuteInOrder(@Param("coinId") int coinid);

    @Modifying
    @Query("UPDATE orderbook set state='execute' where orderid=:orderid and coinid=:coinid")
    Mono<Integer> updateByOrderId(@Param("orderid") int orderid);
}
