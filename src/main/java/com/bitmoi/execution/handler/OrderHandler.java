package com.bitmoi.execution.handler;

import com.bitmoi.execution.domain.Order;
import com.bitmoi.execution.repository.OrderRepository;
import com.bitmoi.execution.service.CoinService;
import com.bitmoi.execution.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Component
@RequiredArgsConstructor
public class OrderHandler {
    @Autowired
    OrderService orderService;
    @Autowired
    CoinService coinService;
    public Mono<ServerResponse> getOrder(ServerRequest request){
        System.out.println("야야야야");
        System.out.println("야야야야");
        System.out.println("야야야야");
        System.out.println("야야야야");
        System.out.println("야야야야");
        System.out.println("야야야야");
        Mono<Integer> executeOrder = request.bodyToMono(Order.class)
                .filter(order -> order.getPrice() > coinService.getCoinPriceById(order).block())
                .flatMap(order -> {
                    return orderService.executeOrder(order);
                })
                .subscribeOn(Schedulers.parallel())
                .log("execute Order get");

        return ok()
                .body(executeOrder, Integer.class)
                .onErrorResume(error -> ServerResponse.badRequest().build())
                .log("execute Order ok");
    }
}
