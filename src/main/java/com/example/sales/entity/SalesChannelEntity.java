package com.example.sales.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

import static jakarta.persistence.GenerationType.IDENTITY;

// SalesChannel.java
@Entity @Table(name="sales_channel")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SalesChannelEntity {
    @Id @GeneratedValue(strategy=IDENTITY) private Long id;
    @Column(nullable=false, unique=true) private String code; // RESTAURANT, PORTAL
    @Column(nullable=false) private String name;
}
