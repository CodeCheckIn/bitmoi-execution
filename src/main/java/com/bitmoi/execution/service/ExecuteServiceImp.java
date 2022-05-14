package com.bitmoi.execution.service;

import com.bitmoi.execution.domain.Execute;
import com.bitmoi.execution.repository.ExecuteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class ExecuteServiceImp implements ExecuteService{
    @Autowired
    ExecuteRepository executeRepository;
    @Override
    public Mono<Execute> save(Execute execute) {
        return executeRepository.save(execute);
    }
}
