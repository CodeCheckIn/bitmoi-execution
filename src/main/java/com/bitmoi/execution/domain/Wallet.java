package com.bitmoi.execution.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Data
@Table(value = "wallet")
@AllArgsConstructor
@NoArgsConstructor
public class Wallet {
    private Long wallet_id;
    private Long user_id;
    private Long coin_id;
    private Double quantity;
    private Double waiting_qty;
    private Double avg_price;

    public Wallet(Long user_id, Long coin_id, Double quantity, Double avg_price) {
        this.user_id = user_id;
        this.coin_id = coin_id;
        this.quantity = quantity;
        this.avg_price = avg_price;
    }
}
