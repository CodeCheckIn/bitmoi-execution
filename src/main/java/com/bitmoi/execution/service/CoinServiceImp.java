package com.bitmoi.execution.service;

import com.bitmoi.execution.domain.Coin;
import com.bitmoi.execution.domain.Order;
import com.bitmoi.execution.repository.CoinRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class CoinServiceImp implements CoinService{
    @Autowired
    CoinRepository coinRepository;
    @Override
    public Mono<Coin> getCoinPriceById(Order order) {
        return coinRepository.getCoinPriceById(order.getCoinid());
    }
}
