package com.example.inventory.controller;

import com.example.inventory.entity.Category;
import com.example.inventory.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:3000")
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
//    @GetMapping(value = "/allCategory", produces = "application/json;charset=UTF-8")
//    public ResponseEntity<List<Category>> getCategories() {
//        return new ResponseEntity<>(categoryService.getCategories(), HttpStatus.OK);
//    }
    @GetMapping(value = "/allCategory", produces = "application/json;charset=UTF-8")
    public ResponseEntity<List<Category>> getCategories() { //@RequestParam(required = false) String title
        try {
            List<Category> categoryList = new ArrayList<>();

            //if (title == null)
            categoryService.getCategories().forEach(categoryList::add);
//            else
//                tutorialRepository.findByTitleContaining(title).forEach(tutorials::add);

            if (categoryList.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            HttpHeaders headers = new HttpHeaders();
            //headers.add("Content-Type", "application/json; charset=UTF-8");
            //headers.add("Access-Control-Allow-Origin", "http://localhost:3000");
            return new ResponseEntity<>(categoryList, headers, HttpStatus.OK);
            //return (new ResponseEntity<Map<String, Object>>(json, headers, HttpStatus.OK));
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/category/{id}")
    public ResponseEntity<Category> getTutorialById(@PathVariable("id") int id) {
        Optional<Category> tutorialData = categoryService.getId(id);
        if (tutorialData.isPresent()) {
            return new ResponseEntity<>(tutorialData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(value = "/category")
    public ResponseEntity<Category> create(@RequestBody Category category) {
        try {
            categoryService.create(new Category(category.getName()));
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
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
