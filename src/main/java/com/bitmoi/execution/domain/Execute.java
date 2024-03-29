package com.bitmoi.execution.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(value = "EXECUTE")
public class Execute {
    private Integer execute_id;
    private Integer order_id;
    private Integer user_id;
    private Integer coin_id;
    private Double price;
    private Double quantity;
    private String types;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime created_at;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
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
