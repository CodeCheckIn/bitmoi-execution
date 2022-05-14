package com.bitmoi.execution.handler;

import com.bitmoi.execution.domain.Execute;
import com.bitmoi.execution.domain.Order;
import com.bitmoi.execution.domain.Wallet;
import com.bitmoi.execution.service.CoinService;
import com.bitmoi.execution.service.ExecuteService;
import com.bitmoi.execution.service.OrderService;
import com.bitmoi.execution.service.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
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
    public static final int KRW_ID = 10;
    @Autowired
    OrderService orderService;
    @Autowired
    CoinService coinService;
    @Autowired
    ExecuteService executeService;
    @Autowired
    WalletService walletService;
    public Mono<ServerResponse> getOrder(ServerRequest request){
        Mono<Execute> executeMono = request.bodyToMono(Order.class)
                .flatMap(order -> {
                    return checkTypeAndPrice(order);
                })
                .flatMap(order -> {
                    return updateOrderbook(order);
                })
                .flatMap(order -> {
                    return saveExecute(order);
                })
//                .flatMap(execute ->{
//                    return updateWallet(execute);
//                })
                .subscribeOn(Schedulers.parallel())
                .log("execute Order get");

        return ok()
                .contentType(APPLICATION_JSON)
                .body(executeMono, Execute.class)
                .onErrorResume(error -> ServerResponse.badRequest().build())
                .log("execute Order ok");
    }

    private Mono<Execute> updateWallet(Execute execute) {
        System.out.println("지갑업데이트");
        return walletService.getWalletByUserId(execute.getUser_id())
                .flatMap(wallet -> {
                    //매수
                    if(execute.getTypes().equals(BID)) {
                        System.out.println(wallet.toString());
                        if (execute.getCoin_id().equals(wallet.getCoin_id())) {
                            wallet.setAvg_price((wallet.getQuantity() * wallet.getAvg_price() + execute.getQuantity() * execute.getPrice()) / wallet.getQuantity() + execute.getQuantity());
                            wallet.setQuantity(wallet.getQuantity() + execute.getQuantity());
                            System.out.println("매수코인저장");
                            return walletService.save(wallet);
                        } else if (wallet.getCoin_id() == KRW_ID) {
                            wallet.setQuantity(wallet.getQuantity() - (execute.getPrice() * execute.getQuantity()));
                            wallet.setWaiting_qty(wallet.getWaiting_qty() - (execute.getPrice() * execute.getQuantity()));
                            System.out.println("KRW 삭제");
                            return walletService.save(wallet);
                        }
                    //매도
                    }else if(execute.getTypes().equals(ASK)){
                        if (execute.getCoin_id().equals(wallet.getCoin_id())) {
                            wallet.setAvg_price(wallet.getQuantity()-execute.getQuantity());
                            wallet.setWaiting_qty(wallet.getWaiting_qty()-execute.getQuantity());
                            return walletService.save(wallet);
                        } else if (wallet.getCoin_id() == KRW_ID) {
                            wallet.setQuantity(wallet.getQuantity()+execute.getQuantity()*execute.getPrice());
                            return walletService.save(wallet);
                        }
                    }
                    return Flux.just(wallet);
                })
                .take(1)
                .next()
                .map(m->execute);
    }

    private Mono<Execute> saveExecute(Order order) {
        return executeService.save(new Execute(order.getOrderid(), order.getUserid(), order.getCoinid(), order.getPrice(), order.getQuantity(), order.getTypes(), LocalDateTime.now()));
    }

    private Mono<Order> updateOrderbook(Order order) {
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
