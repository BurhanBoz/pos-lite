package com.example.sales.controller;

import com.example.sales.entity.SalesChannelEntity;
import com.example.sales.repository.SalesChannelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/channels")
public class ChannelController {

    private final SalesChannelRepository repo;

    public ChannelController(SalesChannelRepository repo) {
        this.repo = repo;
    }

    @GetMapping
    public List<SalesChannelEntity> list() { return repo.findAll(Sort.by("id")); }
}
