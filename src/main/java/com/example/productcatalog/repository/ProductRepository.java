package com.example.productcatalog.repository;

import com.example.productcatalog.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * JPA Repository for the {@link Product}
 *
 * @author Lahiru Jayathilake
 * @since 0.0.1-SNAPSHOT
 */
public interface ProductRepository extends JpaRepository<Product, String> {

    /**
     * Find the list of {@link Product Products} corresponding to
     * the {@link com.example.productcatalog.entity.Category#id Category ID}
     * based on the value provided <code>categoryId</code>
     *
     * @param categoryId ID of the {@link com.example.productcatalog.entity.Category}
     * @return the list of {@link Product}
     */
    List<Product> findByCategories_Id(String categoryId);
}
