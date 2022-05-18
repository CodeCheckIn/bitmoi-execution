package com.bitmoi.execution.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;


@Data
@AllArgsConstructor
@Table(value = "coin")
@NoArgsConstructor
public class Coin {

    @Id
    @Column(value="coin_id")
    private Integer coinId;

    @Column(value = "name")
    private String name;

    @Column(value = "price")
    private Double price;

    @Column(value = "created_at")
    private LocalDateTime createdAt;

    @Column(value = "updated_at")
    private LocalDateTime updatedAt;

    public Coin(Integer coinId, String name, Double price) {
        this.coinId = coinId;
        this.name = name;
        this.price = price;
    }
}
