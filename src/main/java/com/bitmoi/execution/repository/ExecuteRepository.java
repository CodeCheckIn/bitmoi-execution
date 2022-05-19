package com.bitmoi.execution.repository;

import com.bitmoi.execution.domain.Execute;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface ExecuteRepository extends ReactiveCrudRepository<Execute, Integer>{
    @Query("SELECT * FROM EXECUTE WHERE user_id =:userId")
    Flux<Execute> findAllByUserId(Integer userId);
}
