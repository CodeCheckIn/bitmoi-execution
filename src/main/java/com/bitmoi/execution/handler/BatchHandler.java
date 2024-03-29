package com.bitmoi.execution.handler;

import com.bitmoi.execution.domain.Coin;
import com.bitmoi.execution.domain.Execute;
import com.bitmoi.execution.domain.Order;
import com.bitmoi.execution.kafka.KafkaProducerService;
import com.bitmoi.execution.service.ExecuteService;
import com.bitmoi.execution.service.OrderService;
import com.bitmoi.execution.service.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.Disposable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

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
    public static final int KRW_ID = 9999;
    @Autowired
    OrderService orderService;
    @Autowired
    ExecuteService executeService;
    @Autowired
    WalletService walletService;
    @Autowired
    KafkaProducerService kafkaProducerService;
//    @Transactional
//    public Mono<ServerResponse> getBatch(ServerRequest request) {
//        Mono<List<Execute>> mono = request.bodyToMono(Coin.class)
//                .flatMapMany(coin -> {
//                    return checkCoinInfo(coin);
//                })
//                .flatMap(order->{
//                    return updateOrder(order);
//                })
//                .flatMap(order -> {
//                    return saveExecute(order);
//                })
//                .flatMap(execute -> {
//                    return updatedWallet(execute);
//                })
//                .collectList()
//                .subscribeOn(Schedulers.parallel())
//                .log("batch get");
//
//        return ok()
//                .contentType(APPLICATION_JSON)
//                .body(mono, List.class)
//                .onErrorResume(error -> ServerResponse.badRequest().build())
//                .log("batch ok");
//    }

    private Flux<Execute> updatedWallet(Execute execute) {
        System.out.println("=========4.updatedWallet=========");
        return walletService.getWalletByUserId(execute.getUser_id())
                .filter(wallet -> {
                    System.out.println(wallet.toString());
                    System.out.println("wallet Update >>>>");
                    if (execute.getTypes().equals(BID)) {
                        if (execute.getCoin_id().equals(wallet.getCoin_id())) {
                            wallet.setAvg_price((wallet.getQuantity() * wallet.getAvg_price() + execute.getQuantity() * execute.getPrice()) / wallet.getQuantity() + execute.getQuantity());
                            wallet.setQuantity(wallet.getQuantity() + execute.getQuantity());
                        } else if (wallet.getCoin_id() == KRW_ID) {
                            wallet.setQuantity(wallet.getQuantity() - (execute.getPrice() * execute.getQuantity()));
                            wallet.setWaiting_qty(wallet.getWaiting_qty() - (execute.getPrice() * execute.getQuantity()));
                        }
                        System.out.println(wallet.toString());
                        return true;
                    } else if (execute.getTypes().equals(ASK)) {
                        if (execute.getCoin_id().equals(wallet.getCoin_id())) {
                            wallet.setAvg_price(wallet.getQuantity() - execute.getQuantity());
                            wallet.setWaiting_qty(wallet.getWaiting_qty() - execute.getQuantity());
                        } else if (wallet.getCoin_id() == KRW_ID) {
                            wallet.setQuantity(wallet.getQuantity() + execute.getQuantity() * execute.getPrice());
                        }
                        System.out.println(wallet.toString());
                        return true;
                    }
                    System.out.println("Noting updated");
                    return false;
                })
                .flatMap(wallet -> walletService.save(wallet))
                .map(m->{
                    return execute;
                });
    }

    private Mono<Execute> saveExecute(Order order) {
        System.out.println("=========3.saveExecute=========");
        return executeService.save(new Execute(order.getOrderid(), order.getUserid(), order.getCoinid(), order.getPrice(), order.getQuantity(), order.getTypes(), LocalDateTime.now()));
    }
    @Transactional
    public Mono<Order> updateOrder(Order order) {
        System.out.println("=========2.updateOrder=========");
        System.out.println(order.toString());
        order.setState(EXECUTE);
        System.out.println("set Execute state >>");
        System.out.println(order.toString());
        return orderService.save(order);
    }
    @Transactional
    public Flux<Order> checkCoinInfo(Coin coin) {

        return orderService.findAllByCoinId(coin)
                .filter(order -> {
                    System.out.println("=========1.checkCoinInfo=========");
                    return checkOrderBook(coin, order);
                });
    }

    private boolean checkOrderBook(Coin coin, Order order) {
        System.out.println(coin.toString());
        System.out.println(order.toString());
        if(order.getTypes().equals(BID)
        && order.getPrice()>= coin.getPrice()){
            order.setPrice(coin.getPrice());
            System.out.println("Filter return true");
            return true;
        }else if(order.getTypes().equals(ASK)
                && order.getPrice()<= coin.getPrice()){
            order.setPrice(coin.getPrice());
            System.out.println("Filter return true");
            return true;
        }
        System.out.println("Filter return false");
        return false;
    }

    public void getBatch(Coin kafkaCoin) {
        System.out.println("=========Kafka Batch Start=========");
        System.out.printf("%s %s %s \n", kafkaCoin.getCoinId(), kafkaCoin.getName(), kafkaCoin.getPrice());
        Mono.just(kafkaCoin)
                .publishOn(Schedulers.boundedElastic())
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
        }).collectList()
        .doOnNext(execute -> kafkaProducerService.sendExecuteMessage(execute))
        .doOnNext(execute -> {
        })
        .subscribe();
        System.out.println("=========Kafka Batch End=========");
    }
}
