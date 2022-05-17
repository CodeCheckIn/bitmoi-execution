package com.bitmoi.execution.handler;

import com.bitmoi.execution.domain.Coin;
import com.bitmoi.execution.domain.Execute;
import com.bitmoi.execution.domain.Order;
import com.bitmoi.execution.domain.Wallet;
import com.bitmoi.execution.repository.OrderRepository;
import com.bitmoi.execution.service.ExecuteService;
import com.bitmoi.execution.service.OrderService;
import com.bitmoi.execution.service.WalletService;
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
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Component
@RequiredArgsConstructor
public class BatchHandler {
    public static final String BID = "bid";
    public static final String ASK = "ask";
    public static final String EXECUTE = "execute";
    public static final int KRW_ID = 10;
    @Autowired
    OrderService orderService;
    @Autowired
    ExecuteService executeService;
    @Autowired
    WalletService walletService;
    @Transactional
    public Mono<ServerResponse> getBatch(ServerRequest request) {
        Mono<List<Execute>> mono = request.bodyToMono(Coin.class)
                .flatMapMany(coin -> {
                    return checkCoinInfo(coin);
                })
                .flatMap(order->{
                    return updateOrder(order);
                })
                .flatMap(order -> {
                    return saveExecute(order);
                })
                .flatMap(execute -> {
                    return updatedWallet(execute);
                })
                .collectList()
                .subscribeOn(Schedulers.parallel())
                .log("batch get");

        return ok()
                .contentType(APPLICATION_JSON)
                .body(mono, List.class)
                .onErrorResume(error -> ServerResponse.badRequest().build())
                .log("batch ok");
    }

    private Flux<Execute> updatedWallet(Execute execute) {
        return walletService.getWalletByUserId(execute.getUser_id())
                .filter(wallet -> {
                    if (execute.getTypes().equals(BID)) {
                        if (execute.getCoin_id().equals(wallet.getCoin_id())) {
                            wallet.setAvg_price((wallet.getQuantity() * wallet.getAvg_price() + execute.getQuantity() * execute.getPrice()) / wallet.getQuantity() + execute.getQuantity());
                            wallet.setQuantity(wallet.getQuantity() + execute.getQuantity());
                        } else if (wallet.getCoin_id() == KRW_ID) {
                            wallet.setQuantity(wallet.getQuantity() - (execute.getPrice() * execute.getQuantity()));
                            wallet.setWaiting_qty(wallet.getWaiting_qty() - (execute.getPrice() * execute.getQuantity()));
                        }
                        return true;
                    } else if (execute.getTypes().equals(ASK)) {
                        if (execute.getCoin_id().equals(wallet.getCoin_id())) {
                            wallet.setAvg_price(wallet.getQuantity() - execute.getQuantity());
                            wallet.setWaiting_qty(wallet.getWaiting_qty() - execute.getQuantity());
                        } else if (wallet.getCoin_id() == KRW_ID) {
                            wallet.setQuantity(wallet.getQuantity() + execute.getQuantity() * execute.getPrice());
                        }
                        return true;
                    }
                    return false;
                })
                .flatMap(wallet -> walletService.save(wallet))
                .map(m->{
                    return execute;
                });
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
