package com.bitmoi.execution.service;

import com.bitmoi.execution.domain.Wallet;
import com.bitmoi.execution.repository.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class WalletServiceImp implements WalletService{

    @Autowired
    WalletRepository walletRepository;

    @Override
    public Mono<Wallet> save(Wallet wallet) {
        return walletRepository.save(wallet);
    }

    @Override
    public Flux<Wallet> getWalletByUserId(long userId) {
        return walletRepository.findByUserId(userId);
    }
}
