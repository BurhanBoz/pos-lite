package com.example.sales.repository;

import com.example.sales.entity.SalesChannelEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SalesChannelRepository extends JpaRepository<SalesChannelEntity, Long> {
    Optional<SalesChannelEntity> findByCode(String code);
}
