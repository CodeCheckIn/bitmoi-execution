package com.bitmoi.execution.handler;

import com.bitmoi.execution.domain.Coin;
import com.bitmoi.execution.domain.Execute;
import com.bitmoi.execution.domain.Order;
import com.bitmoi.execution.repository.OrderRepository;
import com.bitmoi.execution.service.ExecuteService;
import com.bitmoi.execution.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;
import java.time.LocalDateTime;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Component
@RequiredArgsConstructor
public class BatchHandler {
    public static final String BID = "bid";
    public static final String ASK = "ask";
    public static final String EXECUTE = "execute";
    @Autowired
    OrderService orderService;
    @Autowired
    ExecuteService executeService;
    @Transactional
    public Mono<ServerResponse> getBatch(ServerRequest request) {
        Mono<Coin> mono = request.bodyToMono(Coin.class)
                .flatMapMany(coin -> {
                    return checkCoinInfo(coin);
                })
                .flatMap(order->{
                    return updateOrder(order);
                })
                .flatMap(order -> {
                    return saveExecute(order);
                })
                .next()
                .map(m->{
                    System.out.println(m);
                    return new Coin();
                })
                .subscribeOn(Schedulers.parallel())
                .log("batch get");

        return ok()
                .contentType(APPLICATION_JSON)
                .body(mono, Coin.class)
                .onErrorResume(error -> ServerResponse.badRequest().build())
                .log("batch ok");
    }

    private Mono<Execute> saveExecute(Order order) {
        return executeService.save(new Execute(order.getOrderid(), order.getUserid(), order.getCoinid(), order.getPrice(), order.getQuantity(), order.getTypes(), LocalDateTime.now()));
    }

    private Mono<Order> updateOrder(Order order) {
        order.setTypes(EXECUTE);
        return orderService.save(order);
    }

    private Flux<Order> checkCoinInfo(Coin coin) {
        return orderService.findAllByCoinId(coin)
                .filter(order -> {
                    return checkOrderBook(coin, order);
                });
    }

    private boolean checkOrderBook(Coin coin, Order order) {
        if(order.getTypes().equals(BID)
        && order.getPrice()>= coin.getPrice()){
            order.setPrice(coin.getPrice());
            return true;
        }else if(order.getTypes().equals(ASK)
                && order.getPrice()<= coin.getPrice()){
            order.setPrice(coin.getPrice());
            return true;
        }
        return false;
    }
}
