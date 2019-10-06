package com.example.productcatalog.controller;

import com.example.productcatalog.entity.Category;
import com.example.productcatalog.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * REST controller class for the {@link Category}
 *
 * @author Lahiru Jayathilake
 * @since 0.0.1-SNAPSHOT
 */
@RestController
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    @GetMapping
    public List<Category> getAllCategories() {
        return categoryService.getAllCategories();
    }

    @GetMapping("/{categoryId}")
    public Category getCategory(@PathVariable String categoryId) {
        return categoryService.getCategory(categoryId);
    }

    @PostMapping
    public void createCategory(@RequestBody @Valid Category category) {
        categoryService.createUpdateCategory(category);
    }

    @PutMapping
    public void updateCategory(@RequestBody @Valid Category category) {
        categoryService.createUpdateCategory(category);
    }

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<Void> deleteCategory(@PathVariable String categoryId) {
        categoryService.deleteCategory(categoryId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
