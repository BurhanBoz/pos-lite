package com.example.sales.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import static jakarta.persistence.GenerationType.IDENTITY;
@Entity @Table(name="product_cost")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductCostEntity {
    @Id @GeneratedValue(strategy=IDENTITY) private Long id;
    @ManyToOne(optional=false) private ProductEntity product;
    @Column(nullable=false) private BigDecimal cost;
    @Column(nullable=false) private LocalDateTime effectiveFrom;
    private LocalDateTime effectiveTo; // null => hâlâ geçerli
}
