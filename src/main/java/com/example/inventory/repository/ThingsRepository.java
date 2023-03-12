package com.example.inventory.repository;

import com.example.inventory.entity.Things;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ThingsRepository extends JpaRepository<Things, Integer> {
    List<Things> findAllByCategory(Integer category);

    List<Things> findByNameContainingIgnoreCase(String name);

    List<Things> findByNameContainingIgnoreCaseAndCategory(String name,Integer category);
}

