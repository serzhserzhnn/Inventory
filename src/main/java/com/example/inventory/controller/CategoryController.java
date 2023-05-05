package com.example.inventory.controller;

import com.example.inventory.entity.Category;
import com.example.inventory.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(value = "/inventory", consumes = MediaType.ALL_VALUE)
public class CategoryController {
    /**
     * Добавляем зависимость CategoryService
     */
    CategoryService categoryService;

    @Autowired
    private void setCategoryRepository(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    /**
     * Запрос списка категорий
     *
     * @return JSON-массив {@link Category}
     */

    @GetMapping(value = "/allCategory", produces = "application/json;charset=UTF-8")
    public ResponseEntity<List<Category>> getCategories() {
        try {

            List<Category> categoryList = new ArrayList<>(categoryService.getCategories());
            if (categoryList.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            HttpHeaders headers = new HttpHeaders();

            return new ResponseEntity<>(categoryList, headers, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/category/{id}")
    public ResponseEntity<Category> getTutorialById(@PathVariable("id") int id) {
        Optional<Category> tutorialData = categoryService.getId(id);
        return tutorialData.map(category -> new ResponseEntity<>(category, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/category")
    public ResponseEntity<Category> create(@Valid @RequestBody Category category) {
        try {
            categoryService.create(new Category(category.getName()));
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/category/{id}")
    public ResponseEntity<Category> update(@PathVariable("id") int id,
                                           @Valid @RequestBody Category category) {
        if (categoryService.getId(id).isPresent()) {
            categoryService.update(category);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/category/{id}")
    public ResponseEntity<HttpStatus> deleteCategory(@PathVariable("id") int id) {
        try {
            categoryService.delete(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
