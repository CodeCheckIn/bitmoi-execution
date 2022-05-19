package com.bitmoi.execution.router;

import com.bitmoi.execution.handler.ExecuteHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;

@Component
public class ExecuteRouter {

    @Bean
    public RouterFunction<ServerResponse> order(ExecuteHandler executeHandler){
    return RouterFunctions
            .route(GET("/executions/{userId}"), executeHandler::getMyExecution);
    }

}
