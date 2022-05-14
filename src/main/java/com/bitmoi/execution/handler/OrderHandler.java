package com.bitmoi.execution.handler;

import com.bitmoi.execution.domain.Order;
import com.bitmoi.execution.repository.OrderRepository;
import com.bitmoi.execution.service.CoinService;
import com.bitmoi.execution.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Component
@RequiredArgsConstructor
public class OrderHandler {
    @Autowired
    OrderService orderService;
    @Autowired
    CoinService coinService;
    public Mono<ServerResponse> getOrder(ServerRequest request){
        Mono<Order> orderMono = request.bodyToMono(Order.class)
                .flatMap(order -> {
                    //매수
                    if(order.getTypes().equals("bid")){
                        return coinService.getCoinPriceById(order)
                                .filter(n->order.getPrice()>=n.getPrice())
                                .map(m->{
                                    return order;
                                });
                    //매도
                    }else if(order.getTypes().equals("ask")){
                        return coinService.getCoinPriceById(order)
                                .filter(n->order.getPrice()<=n.getPrice())
                                .map(m->{
                                    return order;
                                });
                    }
                    return Mono.just(new Order());
                })
                .flatMap(order -> {
                    if(order.getCoinid()!=null){
                        order.setState("execute");
                        return orderService.save(order);
                    }
                    return Mono.just(new Order());
                })
                .subscribeOn(Schedulers.parallel())
                .log("execute Order get");

        return ok()
                .contentType(APPLICATION_JSON)
                .body(orderMono, Order.class)
                .onErrorResume(error -> ServerResponse.badRequest().build())
                .log("execute Order ok");
    }
}
