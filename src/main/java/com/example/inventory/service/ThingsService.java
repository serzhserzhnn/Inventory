package com.example.inventory.service;

import com.example.inventory.entity.Things;

import java.util.List;
import java.util.Optional;

public interface ThingsService {
    Optional<Things> getId(int id);

    List<Things> getAll();

    List<Things> findAllByCategory(int category);

    List<Things> findSearch(String name);

    List<Things> findSearchCategory(String name, Integer category);

    void create(Things things);

    void update(Things things);

    void delete(int id);
}
