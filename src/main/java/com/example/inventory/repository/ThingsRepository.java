package com.example.inventory.repository;

import com.example.inventory.entity.Things;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ThingsRepository extends JpaRepository<Things, Integer> {

    Page<Things> findByNameContainingIgnoreCaseAndCategory(String name, Integer category, Pageable paging);

    Page<Things> findByNameContainingIgnoreCase(String name, Pageable pageable);

    Page<Things> findAllByCategory(Integer category, Pageable pageable);
}

