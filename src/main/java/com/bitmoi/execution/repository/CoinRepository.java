package com.bitmoi.execution.repository;

import com.bitmoi.execution.domain.Coin;
import com.bitmoi.execution.domain.Order;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface CoinRepository extends ReactiveCrudRepository<Coin, Integer> {

    @Query("select * from COIN where coin_id =:coinId")
    Mono<Coin> getCoinPriceById(long coinId);
}
