package com.example.sales.service.impl;

import com.example.sales.dto.CostUpsertDto;
import com.example.sales.dto.PriceUpsertDto;
import com.example.sales.dto.ProductCreateUpdateDto;
import com.example.sales.entity.ProductCostEntity;
import com.example.sales.entity.ProductEntity;
import com.example.sales.entity.ProductPriceEntity;
import com.example.sales.entity.SalesChannelEntity;
import com.example.sales.repository.ProductCostRepository;
import com.example.sales.repository.ProductPriceRepository;
import com.example.sales.repository.ProductRepository;
import com.example.sales.repository.SalesChannelRepository;
import com.example.sales.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepo;
    private final ProductCostRepository costRepo;
    private final ProductPriceRepository priceRepo;
    private final SalesChannelRepository channelRepo;

    public ProductServiceImpl(ProductRepository productRepo, ProductCostRepository costRepo,
                              ProductPriceRepository priceRepo, SalesChannelRepository channelRepo) {
        this.productRepo = productRepo;
        this.costRepo = costRepo;
        this.priceRepo = priceRepo;
        this.channelRepo = channelRepo;
    }

    @Override
    @Transactional
    public ProductEntity createOrUpdate(ProductCreateUpdateDto dto, Long id) {
        ProductEntity p = (id == null) ? new ProductEntity() : productRepo.findById(id).orElseThrow();
        p.setName(dto.getName());
        if (dto.getActive() != null) p.setActive(dto.getActive());
        return productRepo.save(p);
    }

    @Override
    @Transactional
    public void upsertCost(Long productId, CostUpsertDto dto) {
        var p = productRepo.findById(productId).orElseThrow();
        var now = dto.getEffectiveFrom() != null ? dto.getEffectiveFrom() : LocalDateTime.now();

        var current = costRepo.findByProductIdAndEffectiveToIsNull(productId).orElse(null);
        if (current != null) {
            // VARSA: sadece güncelle
            current.setCost(dto.getCost());
            current.setEffectiveFrom(now);       // istersen bunu da elleme
            current.setEffectiveTo(null);
            costRepo.save(current);
        } else {
            // YOKSA: ekle
            var pc = new ProductCostEntity();
            pc.setProduct(p);
            pc.setCost(dto.getCost());
            pc.setEffectiveFrom(now);
            pc.setEffectiveTo(null);
            costRepo.save(pc);
        }
    }


    @Override
    @Transactional
    public void upsertPrice(Long productId, PriceUpsertDto dto) {
        var p = productRepo.findById(productId).orElseThrow();
        var ch = channelRepo.findById(dto.getChannelId()).orElseThrow();
        var now = dto.getEffectiveFrom() != null ? dto.getEffectiveFrom() : LocalDateTime.now();

        var current = priceRepo
                .findByProductIdAndSalesChannelIdAndEffectiveToIsNull(productId, dto.getChannelId())
                .orElse(null);

        if (current != null) {
            // VARSA: güncelle
            current.setPrice(dto.getPrice());
            current.setEffectiveFrom(now);       // istersen bunu da elleme
            current.setEffectiveTo(null);
            priceRepo.save(current);
        } else {
            // YOKSA: ekle
            var pp = new ProductPriceEntity();
            pp.setProduct(p);
            pp.setSalesChannel(ch);
            pp.setPrice(dto.getPrice());
            pp.setEffectiveFrom(now);
            pp.setEffectiveTo(null);
            priceRepo.save(pp);
        }
    }

}
