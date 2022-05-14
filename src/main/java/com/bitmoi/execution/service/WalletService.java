package com.bitmoi.execution.service;

import com.bitmoi.execution.domain.Wallet;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface WalletService {
    Mono<Wallet> save(Wallet wallet);

    Flux<Wallet> getWalletByUserId(long userId);
}
