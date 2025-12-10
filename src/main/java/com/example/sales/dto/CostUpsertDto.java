package com.example.sales.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class CostUpsertDto {
    @NotNull
    private BigDecimal cost;
    private LocalDateTime effectiveFrom; // null ise now
}
