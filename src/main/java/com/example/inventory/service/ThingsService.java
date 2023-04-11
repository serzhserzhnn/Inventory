package com.example.inventory.service;

import com.example.inventory.entity.Things;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ThingsService {
    Optional<Things> getId(UUID id);

    List<Things> getAll();

    Page<Things> getAll(Pageable paging);

    Page<Things> findByNameContaining(String name, Pageable paging);

    Page<Things> findAllByCategory(int category, Pageable paging);

    Page<Things> findSearchCategory(Integer category, String name, Pageable paging);

    void create(Things things);

    void update(Things things);

    void delete(UUID id);
}
