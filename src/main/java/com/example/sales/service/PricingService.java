package com.example.sales.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public interface PricingService {

    public BigDecimal getActiveCost(Long productId, LocalDateTime at);
    public BigDecimal getActivePrice(Long productId, Long channelId, LocalDateTime at);
}
