package com.example.productcatalog.controller;

import com.example.productcatalog.entity.Category;
import com.example.productcatalog.entity.Product;
import com.example.productcatalog.service.CategoryService;
import com.example.productcatalog.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * REST controller class for the {@link Product}
 *
 * @author Lahiru Jayathilake
 * @since 0.0.1-SNAPSHOT
 */
@RestController
public class ProductController {

    @Autowired
    ProductService productService;

    @Autowired
    CategoryService categoryService;

    @RequestMapping("/categories/{categoryId}/products")
    public List<Product> getProducts(@PathVariable String categoryId) {
        return productService.getProducts(categoryId);
    }

    @PostMapping("/categories/{categoryId}/products")
    public Product createProductFromCategory(@PathVariable String categoryId, @RequestBody @Valid Product product) {
        product.setCategories(Collections.singletonList(categoryService.getCategory(categoryId)));

        return productService.createUpdateProduct(product);
    }

    @PostMapping("/products")
    public Product createProduct(@RequestBody @Valid Product product) {
        List<Category> categories = new ArrayList<>();
        if (!product.getCategories().isEmpty()) {
            product.getCategories().forEach(c -> categories.add(categoryService.getCategory(c.getId())));
        }
        product.setCategories(categories);

        return productService.createUpdateProduct(product);
    }

    @PutMapping("/products")
    public Product updateProduct(@RequestBody @Valid Product product) {
        List<Category> categories = new ArrayList<>();
        // If categories have been removed that will reflect in the product
        if (!product.getCategories().isEmpty()) {
            // If there categories load the correct values
            product.getCategories().forEach(c -> categories.add(categoryService.getCategory(c.getId())));
        }
        product.setCategories(categories);

        return productService.createUpdateProduct(product);
    }

    @DeleteMapping("products/{productId}")
    public ResponseEntity<Void> deleteProduct(@PathVariable String productId) {
        Product product = productService.getProduct(productId);
        List<Category> categories = categoryService.getCategoriesByProductId(productId);
        categories.forEach(c -> c.removeProduct(product));
        productService.deleteProduct(product);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
