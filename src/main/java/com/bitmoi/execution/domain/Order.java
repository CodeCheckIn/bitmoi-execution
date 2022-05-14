package com.bitmoi.execution.domain;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.sql.Date;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(value = "orderbook")
public class Order {

    @Id
    private Integer orderid;

    private Integer userid;

    private Integer coinid;

    private Double price;

    private Double quantity;

    private String types;

    private String state;

    private String createdat;

    private String updatedat;

}