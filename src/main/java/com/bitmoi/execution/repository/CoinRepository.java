package com.bitmoi.execution.repository;

import com.bitmoi.execution.domain.Coin;
import com.bitmoi.execution.domain.Order;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface CoinRepository extends ReactiveCrudRepository<Coin, Integer> {

    @Query("select price from coin \n" +
            "where coin_id =:coinId \n" +
            "order by updated_at DESC \n" +
            "limit 1")
    Mono<Double> getCoinPriceById(long coinId);
}
