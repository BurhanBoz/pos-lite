package com.example.sales.controller;

import com.example.sales.dto.CostUpsertDto;
import com.example.sales.dto.PriceUpsertDto;
import com.example.sales.dto.ProductCreateUpdateDto;
import com.example.sales.entity.ProductCostEntity;
import com.example.sales.entity.ProductEntity;
import com.example.sales.entity.ProductPriceEntity;
import com.example.sales.repository.ProductCostRepository;
import com.example.sales.repository.ProductPriceRepository;
import com.example.sales.repository.ProductRepository;
import com.example.sales.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    private final ProductService productService;
    private final ProductRepository productRepo;
    private final ProductPriceRepository priceRepo;
    private final ProductCostRepository costRepo;

    public ProductController(ProductService productService, ProductRepository productRepo,
                             ProductPriceRepository priceRepo, ProductCostRepository costRepo) {
        this.productService = productService;
        this.productRepo = productRepo;
        this.priceRepo = priceRepo;
        this.costRepo = costRepo;
    }

    @PostMapping
    public ProductEntity create(@Valid @RequestBody ProductCreateUpdateDto dto) {
        return productService.createOrUpdate(dto, null);
    }

    @PutMapping("/{id}")
    public ProductEntity update(@PathVariable Long id, @Valid @RequestBody ProductCreateUpdateDto dto) {
        return productService.createOrUpdate(dto, id);
    }

    @GetMapping
    public List<ProductEntity> list() { return productRepo.findAll(Sort.by("name")); }

    @PostMapping("/{id}/costs")
    public void upsertCost(@PathVariable Long id, @Valid @RequestBody CostUpsertDto dto) {
        productService.upsertCost(id, dto);
    }

    @PostMapping("/{id}/prices")
    public void upsertPrice(@PathVariable Long id, @Valid @RequestBody PriceUpsertDto dto) {
        productService.upsertPrice(id, dto);
    }

    @GetMapping("/{id}/costs")
    public List<ProductCostEntity> costs(@PathVariable Long id) {
        return costRepo.findAll(
                (root, q, cb) -> cb.equal(root.get("product").get("id"), id),
                Sort.by(Sort.Direction.DESC, "effectiveFrom"));
    }

    @GetMapping("/{id}/prices")
    public List<ProductPriceEntity> prices(@PathVariable Long id) {
        return priceRepo.findAll(
                (root, q, cb) -> cb.equal(root.get("product").get("id"), id),
                Sort.by(Sort.Direction.DESC, "effectiveFrom"));
    }
}
