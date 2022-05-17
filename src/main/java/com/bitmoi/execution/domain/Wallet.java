package com.bitmoi.execution.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Data
@Table(value = "WALLET")
@AllArgsConstructor
@NoArgsConstructor
public class Wallet {
    private Integer wallet_id;
    private Integer user_id;
    private Integer coin_id;
    private double quantity;
    private double waiting_qty;
    private double avg_price;

    public Wallet(Integer user_id, Integer coin_id, Double quantity, Double avg_price) {
        this.user_id = user_id;
        this.coin_id = coin_id;
        this.quantity = quantity;
        this.avg_price = avg_price;
    }
}
