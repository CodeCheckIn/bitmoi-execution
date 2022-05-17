package com.bitmoi.execution.domain;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Table(value = "bitmoi.ORDERBOOK")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Orderbook {

    @Id
    @Column(value = "orderbook_id")
    private Integer orderbookid;

    @Column(value = "user_id")
    private Integer userid;

    @Column(value = "coin_id")
    private Integer coinid;

    @Column(value = "price")
    private BigDecimal price;

    @Column(value = "quantity")
    private BigDecimal quantity;

    @Column(value = "types")
    private String types;

    @Column(value = "state")
    private String state;

    @CreatedDate
    @Column(value = "created_at")
    private LocalDateTime createdat;

    @LastModifiedDate
    @Column(value = "updated_at")
    private LocalDateTime updatedat;

}
