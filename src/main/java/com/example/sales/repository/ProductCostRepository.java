package com.example.sales.repository;

import com.example.sales.entity.ProductCostEntity;
import com.example.sales.entity.ProductPriceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProductCostRepository extends JpaRepository<ProductCostEntity, Long>,
        JpaSpecificationExecutor<ProductCostEntity> {
    @Query("""
     SELECT pc FROM ProductCostEntity pc
     WHERE pc.product.id = :productId
       AND pc.effectiveFrom <= :at
       AND (pc.effectiveTo IS NULL OR :at < pc.effectiveTo)
     ORDER BY pc.effectiveFrom DESC
     """)
    List<ProductCostEntity> findActiveAt(@Param("productId") Long productId, @Param("at") LocalDateTime at);

    Optional<ProductCostEntity> findByProductIdAndEffectiveToIsNull(Long productId);

}
