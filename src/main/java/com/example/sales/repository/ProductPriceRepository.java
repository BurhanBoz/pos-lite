package com.example.sales.repository;

import com.example.sales.entity.ProductCostEntity;
import com.example.sales.entity.ProductPriceEntity;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ProductPriceRepository extends JpaRepository<ProductPriceEntity, Long>,
        JpaSpecificationExecutor<ProductPriceEntity> {
    @Query("""
     SELECT pp FROM ProductPriceEntity pp
     WHERE pp.product.id = :productId
       AND pp.salesChannel.id = :channelId
       AND pp.effectiveFrom <= :at
       AND (pp.effectiveTo IS NULL OR :at < pp.effectiveTo)
     ORDER BY pp.effectiveFrom DESC
     """)
    List<ProductPriceEntity> findActiveAt(@Param("productId") Long productId,
                                          @Param("channelId") Long channelId,
                                          @Param("at") LocalDateTime at);

    Optional<ProductPriceEntity> findByProductIdAndSalesChannelIdAndEffectiveToIsNull(Long productId, Long salesChannelId);

}
