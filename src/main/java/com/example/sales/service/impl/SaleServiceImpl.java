package com.example.sales.service.impl;

import com.example.sales.dto.DaySummaryDto;
import com.example.sales.dto.SaleCreateDto;
import com.example.sales.entity.SaleEntryEntity;
import com.example.sales.repository.DaySummaryView;
import com.example.sales.repository.ProductRepository;
import com.example.sales.repository.SaleEntryRepository;
import com.example.sales.repository.SalesChannelRepository;
import com.example.sales.service.PricingService;
import com.example.sales.service.SaleService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class SaleServiceImpl implements SaleService {

    private final SaleEntryRepository saleRepo;
    private final ProductRepository productRepo;
    private final SalesChannelRepository channelRepo;
    private final PricingService pricingService;

    public SaleServiceImpl(SaleEntryRepository saleRepo, ProductRepository productRepo, SalesChannelRepository channelRepo, PricingService pricingService) {
        this.saleRepo = saleRepo;
        this.productRepo = productRepo;
        this.channelRepo = channelRepo;
        this.pricingService = pricingService;
    }

    @Override
    @Transactional
    public SaleEntryEntity createSale(SaleCreateDto dto) {
        var p = productRepo.findById(dto.getProductId()).orElseThrow();
        var ch = channelRepo.findById(dto.getChannelId()).orElseThrow();
        var when = dto.getSaleDatetime() != null ? dto.getSaleDatetime() : LocalDateTime.now();

        BigDecimal unitCost = pricingService.getActiveCost(p.getId(), when);
        BigDecimal unitPrice = pricingService.getActivePrice(p.getId(), ch.getId(), when);

        if (unitPrice.compareTo(BigDecimal.ZERO) <= 0)
            throw new IllegalStateException("Geçerli satış fiyatı bulunamadı.");

        var se = new SaleEntryEntity();
        se.setProduct(p);
        se.setSalesChannel(ch);
        se.setSaleDatetime(when);
        se.setQuantity(dto.getQuantity());
        se.setUnitCostSnapshot(unitCost != null ? unitCost : BigDecimal.ZERO);
        se.setUnitPriceSnapshot(unitPrice);
        se.setNote(dto.getNote());
        return saleRepo.save(se);

    }

    @Override
    @Transactional(readOnly = true)
    public List<SaleEntryEntity> listToday() {
        return saleRepo.findToday();
    }

    @Override
    @Transactional(readOnly = true)
    public DaySummaryDto daySummary(LocalDate date) {
        LocalDateTime start = date.atStartOfDay();
        LocalDateTime end   = date.plusDays(1).atStartOfDay();

        DaySummaryView v = saleRepo.sumForRange(start, end);

        BigDecimal revenue = v != null && v.getRevenue() != null ? v.getRevenue() : BigDecimal.ZERO;
        BigDecimal cost    = v != null && v.getCost()    != null ? v.getCost()    : BigDecimal.ZERO;
        BigDecimal profit  = revenue.subtract(cost);

        return new DaySummaryDto(date, revenue, cost, profit);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SaleEntryEntity> listForRange(LocalDateTime start, LocalDateTime end) {
        return saleRepo.findForRange(start, end);
    }

    @Override
    public List<SaleEntryEntity> findBetween(LocalDateTime start, LocalDateTime end) {
        return saleRepo.findForRange(start, end);
    }
}
