package com.bitmoi.execution.service;

import com.bitmoi.execution.domain.Order;
import com.bitmoi.execution.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class OrderServiceImp implements OrderService{
    @Autowired
    OrderRepository orderRepository;

    @Override
    public Mono<Integer> executeOrder(Order order) {
        System.out.println(order.getCoinid());
        if(order.getCoinid() != null) return orderRepository.updateByIsExecuteInOrder(order.getCoinid());
        return Mono.just(0);
    }

    @Override
    public Mono<Order> save(Order order) {
        return orderRepository.save(order);
    }
}
