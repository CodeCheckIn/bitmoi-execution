package com.bitmoi.execution.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(value = "ORDERBOOK")
public class Order {

    @Id
    @Column(value = "orderbook_id")
    private Integer order_id;
    @Column(value = "user_id")
    private Integer user_id;
    @Column(value = "coin_id")
    private Integer coin_id;

    private Double price;

    private Double quantity;

    private String types;

    private String state;
    @Column(value = "created_at")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdat;
    @Column(value = "updated_at")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedat;

}