package com.bitmoi.execution.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Table(value = "Execute")
public class Execute {
    private Long execute_id;
    private Long order_id;
    private Long user_id;
    private Long coin_id;
    private Double price;
    private Double quantity;
    private String types;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;

    public Execute(Long order_id, Long user_id, Long coin_id, Double price, Double quantity, String types, LocalDateTime created_at) {
        this.order_id = order_id;
        this.user_id = user_id;
        this.coin_id = coin_id;
        this.price = price;
        this.quantity = quantity;
        this.types = types;
        this.created_at = created_at;
    }
}
