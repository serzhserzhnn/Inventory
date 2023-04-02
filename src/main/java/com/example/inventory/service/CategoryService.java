package com.example.inventory.service;

import com.example.inventory.entity.Category;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface CategoryService {
    List<Category> getCategories();

    Optional<Category> getId(int id);

    void create(Category category);

    void delete(int id);

    void update(Category category);
}
