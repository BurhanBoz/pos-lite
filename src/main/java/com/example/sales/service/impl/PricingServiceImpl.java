package com.example.sales.service.impl;

import com.example.sales.repository.ProductCostRepository;
import com.example.sales.repository.ProductPriceRepository;
import com.example.sales.service.PricingService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class PricingServiceImpl implements PricingService {

    private final ProductCostRepository costRepo;
    private final ProductPriceRepository priceRepo;

    public PricingServiceImpl(ProductCostRepository costRepo, ProductPriceRepository priceRepo) {
        this.costRepo = costRepo;
        this.priceRepo = priceRepo;
    }

    @Override
    public BigDecimal getActiveCost(Long productId, LocalDateTime at) {
        var list = costRepo.findActiveAt(productId, at);
        return list.isEmpty() ? BigDecimal.ZERO : list.get(0).getCost();
    }

    @Override
    public BigDecimal getActivePrice(Long productId, Long channelId, LocalDateTime at) {
        var list = priceRepo.findActiveAt(productId, channelId, at);
        return list.isEmpty() ? BigDecimal.ZERO : list.get(0).getPrice();
    }
}
