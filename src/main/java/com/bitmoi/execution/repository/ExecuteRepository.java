package com.bitmoi.execution.repository;

import com.bitmoi.execution.domain.Execute;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExecuteRepository extends ReactiveCrudRepository<Execute, Integer>{

}
