package com.example.sales.service;

import com.example.sales.dto.DaySummaryDto;
import com.example.sales.dto.SaleCreateDto;
import com.example.sales.entity.SaleEntryEntity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface SaleService {

    public SaleEntryEntity createSale(SaleCreateDto dto);
    public List<SaleEntryEntity> listToday();
    public DaySummaryDto daySummary(LocalDate date);
    public List<SaleEntryEntity> listForRange(LocalDateTime start, LocalDateTime end);
}
