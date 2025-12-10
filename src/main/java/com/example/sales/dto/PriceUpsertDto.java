package com.example.sales.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class PriceUpsertDto {
    @NotNull
    private Long channelId;
    @NotNull private BigDecimal price;
    private LocalDateTime effectiveFrom; // null ise now
}
