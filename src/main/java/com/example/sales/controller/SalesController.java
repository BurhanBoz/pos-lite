package com.example.sales.controller;

import com.example.sales.dto.DaySummaryDto;
import com.example.sales.dto.SaleCreateDto;
import com.example.sales.entity.SaleEntryEntity;
import com.example.sales.repository.SaleEntryRepository;
import com.example.sales.repository.SalesChannelRepository;
import com.example.sales.service.SaleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/sales")
public class SalesController {

    private final SaleService saleService;

    private final SaleEntryRepository saleRepo;

    public SalesController(SaleService saleService, SaleEntryRepository saleRepo) {
        this.saleService = saleService;
        this.saleRepo = saleRepo;
    }

    @PostMapping
    public SaleEntryEntity create(@Valid @RequestBody SaleCreateDto dto) {
        return saleService.createSale(dto);
    }

    @GetMapping("/today")
    public List<SaleEntryEntity> today() { return saleService.listToday(); }

    @GetMapping("/summary")
    public DaySummaryDto summary(@RequestParam(required=false) @DateTimeFormat(iso=DateTimeFormat.ISO.DATE) LocalDate date) {
        LocalDate d = (date != null) ? date : LocalDate.now();
        return saleService.daySummary(d);
    }

    // com.example.sales.controller.SalesController.java
    @GetMapping("/by-date")
    public List<SaleEntryEntity> byDate(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        LocalDateTime start = date.atStartOfDay();
        LocalDateTime end   = date.plusDays(1).atStartOfDay();
        return saleService.listForRange(start, end);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        saleRepo.deleteById(id);
    }

    @GetMapping("/range")
    public List<SaleEntryEntity> range(@RequestParam String from, @RequestParam String to) {
        LocalDate f = LocalDate.parse(from);
        LocalDate t = LocalDate.parse(to);

        LocalDateTime start = f.atStartOfDay();
        LocalDateTime end = t.plusDays(1).atStartOfDay().minusNanos(1); // inclusive

        return saleService.findBetween(start, end);
    }

}
