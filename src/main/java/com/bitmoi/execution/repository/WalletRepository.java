package com.bitmoi.execution.repository;

import com.bitmoi.execution.domain.Wallet;
import org.springframework.data.r2dbc.repository.Modifying;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface WalletRepository extends ReactiveCrudRepository<Wallet, Integer> {
    @Query("select * from WALLET where user_id =:user_id")
    Flux<Wallet> findByUserId(long user_id);

    @Modifying
    @Query("UPDATE WALLET Set quantity =:quantity, waiting_qty =:waiting_qty, avg_price =:avg_price where wallet_id=:wallet_id ")
    Mono<Integer> updateWallet(double quantity,double waiting_qty, double avg_price, long wallet_id);
}
