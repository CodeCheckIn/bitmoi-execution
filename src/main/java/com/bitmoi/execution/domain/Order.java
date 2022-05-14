package com.bitmoi.execution.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(value = "orderbook")
public class Order {

    @Id
    private Long orderid;

    private Long userid;

    private Long coinid;

    private Double price;

    private Double quantity;

    private String types;

    private String state;

    private String createdat;

    private String updatedat;

}