package com.example.sales.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity @Table(name="sale_entry")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SaleEntryEntity {
    @Id
    @GeneratedValue(strategy=IDENTITY) private Long id;
    @Column(nullable=false) private LocalDateTime saleDatetime = LocalDateTime.now();
    @ManyToOne(optional=false) private ProductEntity product;
    @ManyToOne(optional=false) private SalesChannelEntity salesChannel;
    @Column(nullable=false) private Integer quantity;
    @Column(nullable=false) private BigDecimal unitCostSnapshot;
    @Column(nullable=false) private BigDecimal unitPriceSnapshot;
    private String note;
    // total_cost, total_price, profit DB tarafında hesaplanıyor; istersen burada @Formula da kullanılabilir.
}
