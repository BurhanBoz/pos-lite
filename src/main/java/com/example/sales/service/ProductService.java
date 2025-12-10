package com.example.sales.service;

import com.example.sales.dto.CostUpsertDto;
import com.example.sales.dto.PriceUpsertDto;
import com.example.sales.dto.ProductCreateUpdateDto;
import com.example.sales.entity.ProductEntity;

public interface ProductService {

    public ProductEntity createOrUpdate(ProductCreateUpdateDto dto, Long id);
    public void upsertCost(Long productId, CostUpsertDto dto);
    public void upsertPrice(Long productId, PriceUpsertDto dto);
}
