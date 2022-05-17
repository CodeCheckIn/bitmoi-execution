package com.bitmoi.execution.handler;

import com.bitmoi.execution.domain.Coin;
import com.bitmoi.execution.domain.Execute;
import com.bitmoi.execution.domain.Order;
import com.bitmoi.execution.repository.OrderRepository;
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

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Component
@RequiredArgsConstructor
public class BatchHandler {
    public static final String BID = "bid";
    public static final String ASK = "ask";
    @Autowired
    OrderService orderService;
    @Transactional
    public Mono<ServerResponse> getBatch(ServerRequest request) {
        Mono<Coin> mono = request.bodyToMono(Coin.class)
                .flatMap(coin -> {
                    return orderService.findAllByCoinId(coin) //coinid 같고 wait 상태 가져오기.
                            .filter(order -> {
                                return checkOrderBook(coin, order);
                            })
                            .flatMap(order->{
                                System.out.println("야야야야야");
                                System.out.println(order.toString());
                                return null;
                            })
                            .next().map(m->{
                                return coin;
                            });
                })
                .subscribeOn(Schedulers.parallel())
                .log("batch get");

        return ok()
                .contentType(APPLICATION_JSON)
                .body(mono, Coin.class)
                .onErrorResume(error -> ServerResponse.badRequest().build())
                .log("batch ok");
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
