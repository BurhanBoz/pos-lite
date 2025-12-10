package com.example.sales.repository;

import java.math.BigDecimal;

public interface DaySummaryView {

    BigDecimal getRevenue();
    BigDecimal getCost();
    // İstersen burada default ile profit hesaplayabilirsin:
    default BigDecimal getProfit() {
        return (getRevenue() != null ? getRevenue() : BigDecimal.ZERO)
                .subtract(getCost() != null ? getCost() : BigDecimal.ZERO);
    }
}
