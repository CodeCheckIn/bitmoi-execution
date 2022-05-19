package com.bitmoi.execution.handler;

import com.bitmoi.execution.config.JwtDecode;
import com.bitmoi.execution.domain.Execute;
import com.bitmoi.execution.domain.User;
import com.bitmoi.execution.service.ExecuteService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Component
@RequiredArgsConstructor
public class ExecuteHandler {
    @Autowired
    ExecuteService executeService;
    @Autowired
    JwtDecode jwtDecode;
    public Mono<ServerResponse> getMyExecution(ServerRequest request) {
//        Integer userId = jwtDecode.decode(request.headers().asHttpHeaders().getFirst("Authorization"));
        Integer userId = Integer.valueOf(request.pathVariable("userId"));
        Mono<List<Execute>> mono = executeService.getMyExecution(userId)
                .collectList()
                .subscribeOn(Schedulers.parallel())
                .log("batch get");

        return ok()
                .contentType(APPLICATION_JSON)
                .body(mono, List.class)
                .onErrorResume(error -> ServerResponse.badRequest().build())
                .log("batch ok");
    }
}
