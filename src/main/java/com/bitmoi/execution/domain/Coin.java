package com.bitmoi.execution.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.sql.Date;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Table(value = "coin")
public class Coin {

    @Id
    @Column(value="coin_id")
    private Long coinId;

    @Column(value = "name")
    private String name;

    @Column(value = "price")
    private Double price;
//
//    @Column(value = "updated_at")
//    private LocalDateTime updated_at;
}
