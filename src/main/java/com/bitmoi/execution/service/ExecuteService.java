package com.bitmoi.execution.service;

import com.bitmoi.execution.domain.Execute;
import com.bitmoi.execution.domain.User;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ExecuteService {
    Mono<Execute> save(Execute execute);
    Flux<Execute> getMyExecution(Integer userId);
}
