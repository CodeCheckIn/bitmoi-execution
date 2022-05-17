package com.bitmoi.execution.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Table(value = "EXECUTE")
public class Execute {
    private Integer execute_id;
    private Integer order_id;
    private Integer user_id;
    private Integer coin_id;
    private Double price;
    private Double quantity;
    private String types;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;

    public Execute(Integer order_id, Integer user_id, Integer coin_id, Double price, Double quantity, String types, LocalDateTime created_at) {
        this.order_id = order_id;
        this.user_id = user_id;
        this.coin_id = coin_id;
        this.price = price;
        this.quantity = quantity;
        this.types = types;
        this.created_at = created_at;
    }
}
