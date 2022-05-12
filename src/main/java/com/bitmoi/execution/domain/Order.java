package com.bitmoi.execution.domain;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(value = "orderbook")
public class Order {

    @Id
    @Column(value = "orderid")
    private Integer orderid;

    @Column(value = "userid")
    private Integer userid;

    @Column(value = "coinid")
    private Integer coinid;

    @Column(value = "price")
    private Double price;

    @Column(value = "quantity")
    private Double quantity;

    @Column(value = "types")
    private String types;

    @Column(value = "state")
    private String state;

    @CreatedDate
    @Column(value = "createdat")
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(value = "updateddt")
    private LocalDateTime updatedAt;

}