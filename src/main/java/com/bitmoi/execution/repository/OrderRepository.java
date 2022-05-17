package com.bitmoi.execution.repository;

import com.bitmoi.execution.domain.Order;
import org.springframework.data.r2dbc.repository.Modifying;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface OrderRepository extends ReactiveCrudRepository<Order, Integer> {

    @Modifying
    @Query("UPDATE ORDERBOOK set state='execute' where state='wait' and coin_id=:coinid")
    Mono<Integer> updateByIsExecuteInOrder(@Param("coinId") long coinid);

    @Query("SELECT * FROM ORDERBOOK WHERE coin_id=:coinId AND state='wait'")
    Flux<Order> findAllByCoinId(long coinId);
}
