package com.example.sales.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Table(name="product")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductEntity {
    @Id
    @GeneratedValue(strategy=IDENTITY) private Long id;
    @Column(nullable=false, unique=true) private String name;
    @Column(nullable=false) private boolean isActive = true;
    @Column(nullable=false, updatable=false) private Timestamp createdAt = new Timestamp(System.currentTimeMillis());
    @Column(nullable=false) private Timestamp updatedAt = new Timestamp(System.currentTimeMillis());
    @PreUpdate void onUpdate(){ this.updatedAt = new Timestamp(System.currentTimeMillis()); }
}
