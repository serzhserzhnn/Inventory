package com.example.inventory.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.inventory.entity.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
}
