package com.example.productcatalog.service;

import com.example.productcatalog.entity.Category;
import com.example.productcatalog.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service class implementation for the {@link ICategoryService}
 *
 * @author Lahiru Jayathilake
 * @since 0.0.1-SNAPSHOT
 */
@Service
public class CategoryService implements ICategoryService {

    @Autowired
    CategoryRepository categoryRepository;

    @Override
    public Category getCategory(String categoryId) {
        return categoryRepository.findById(categoryId).orElse(null);
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Category createUpdateCategory(Category category) {
       return categoryRepository.save(category);
    }

    @Override
    public List<Category> getCategoriesByProductId(String productId) {
        return categoryRepository.findByProducts_Id(productId);
    }

    @Override
    public void deleteCategory(String categoryId) {
        categoryRepository.deleteById(categoryId);
    }
}
