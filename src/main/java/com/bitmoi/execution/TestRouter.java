package com.bitmoi.execution;

import com.bitmoi.execution.handler.OrderHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;

@Component
public class TestRouter {
//
//    @Bean
//    public RouterFunction<ServerResponse> index(TestHandler testHandler){
//        return RouterFunctions
//                .route(GET("/test").and(accept(MediaType.APPLICATION_JSON)), testHandler::getTables);
//    }
    @Bean
    public RouterFunction<ServerResponse> order(OrderHandler orderHandler){
        return RouterFunctions
                .route(GET("/order").and(accept(MediaType.APPLICATION_JSON)), orderHandler::getOrder);
    }
}
