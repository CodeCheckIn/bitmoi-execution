package com.bitmoi.execution.handler;

import com.bitmoi.execution.domain.Execute;
import com.bitmoi.execution.domain.Order;
import com.bitmoi.execution.service.CoinService;
import com.bitmoi.execution.service.ExecuteService;
import com.bitmoi.execution.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.time.LocalDateTime;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Component
@RequiredArgsConstructor
public class OrderHandler {
    public static final String EXECUTE = "execute";
    public static final String BID = "bid";
    public static final String ASK = "ask";
    @Autowired
    OrderService orderService;
    @Autowired
    CoinService coinService;
    @Autowired
    ExecuteService executeService;
    public Mono<ServerResponse> getOrder(ServerRequest request){
        Mono<Execute> executeMono = request.bodyToMono(Order.class)
                .flatMap(order -> {
                    return checkTypeAndPrice(order);
                })
                .flatMap(order -> {
                    return executeOrderbook(order);
                })
                .flatMap(order -> {
                    return saveExecute(order);
                })
//                .flatMap(execute ->{
//                    return executeService.save(new Execute(order.getOrderid(),order.getUserid(),order.getCoinid(),order.getPrice(),order.getQuantity(),order.getTypes(), LocalDateTime.now()));
//                })
                .subscribeOn(Schedulers.parallel())
                .log("execute Order get");

        return ok()
                .contentType(APPLICATION_JSON)
                .body(executeMono, Execute.class)
                .onErrorResume(error -> ServerResponse.badRequest().build())
                .log("execute Order ok");
    }

    private Mono<Execute> saveExecute(Order order) {
        return executeService.save(new Execute(order.getOrderid(), order.getUserid(), order.getCoinid(), order.getPrice(), order.getQuantity(), order.getTypes(), LocalDateTime.now()));
    }

    private Mono<Order> executeOrderbook(Order order) {
        if(order.getCoinid()!=null){
            order.setState(EXECUTE);
            return orderService.save(order);
        }
        return Mono.just(new Order());
    }

    private Mono<Order> checkTypeAndPrice(Order order) {
        //매수
        if(order.getTypes().equals(BID)){
            return coinService.getCoinPriceById(order)
                    .filter(n -> order.getPrice() >= n.getPrice())
                    .map(m -> {
                        order.setPrice(m.getPrice());
                        return order;
                    });
        //매도
        }else if(order.getTypes().equals(ASK)){
            return coinService.getCoinPriceById(order)
                    .filter(n -> order.getPrice() <= n.getPrice())
                    .map(m -> {
                        order.setPrice(m.getPrice());
                        return order;
                    });
        }
        return Mono.just(new Order());
    }
}
