package com.bitmoi.execution.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.sql.Date;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(value = "orderbook")
public class Order {

    @Id
    @Column(value = "orderbook_id")
    private Long orderid;
    @Column(value = "user_id")
    private Long userid;

    private Long coinid;

    private Double price;

    private Double quantity;

    private String types;

    private String state;

    private Date createdat;

    private Date updatedat;

}