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

    @GetMapping("/categories/{categoryId}/products")
    public ResponseEntity<List<Product>> getProducts(@PathVariable String categoryId) {
        List<Product> products = productService.getProducts(categoryId);

        return products.size() > 0
                ? new ResponseEntity<>(products, HttpStatus.FOUND)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/categories/{categoryId}/products")
    public ResponseEntity<Product> createProductFromCategory(@PathVariable String categoryId, @RequestBody @Valid Product product) {
        product.setCategories(Collections.singletonList(categoryService.getCategory(categoryId)));
        Product createdProduct = productService.createUpdateProduct(product);

        return new ResponseEntity<>(createdProduct, HttpStatus.CREATED);
    }

    @PostMapping("/products")
    public ResponseEntity<Product> createProduct(@RequestBody @Valid Product product) {
        List<Category> categories = new ArrayList<>();
        if (!product.getCategories().isEmpty()) {
            product.getCategories().forEach(c -> categories.add(categoryService.getCategory(c.getId())));
        }
        product.setCategories(categories);

        return new ResponseEntity<>(product, HttpStatus.CREATED);
    }

    @PutMapping("/products")
    public ResponseEntity<Product> updateProduct(@RequestBody @Valid Product product) {
        if (productService.getProduct(product.getId()) != null) {
            List<Category> categories = new ArrayList<>();
            // If categories have been removed that will reflect in the product
            if (!product.getCategories().isEmpty()) {
                // If there categories load the correct values
                product.getCategories().forEach(c -> categories.add(categoryService.getCategory(c.getId())));
            }
            product.setCategories(categories);

            return new ResponseEntity<>(productService.createUpdateProduct(product), HttpStatus.OK);

        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @DeleteMapping("products/{productId}")
    public ResponseEntity<Void> deleteProduct(@PathVariable String productId) {
        Product product = productService.getProduct(productId);

        if (product != null) {
            List<Category> categories = categoryService.getCategoriesByProductId(productId);
            categories.forEach(c -> c.removeProduct(product));
            productService.deleteProduct(product);

            return new ResponseEntity<>(HttpStatus.OK);

        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }
}
