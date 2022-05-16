package com.bitmoi.execution.service;

import com.bitmoi.execution.domain.Execute;
import reactor.core.publisher.Mono;

public interface ExecuteService {
    Mono<Execute> save(Execute execute);
}
