package com.example.productcatalog.repository;

import com.example.productcatalog.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * JPA Repository for the {@link Category}
 *
 * @author Lahiru Jayathilake
 * @since 0.0.1-SNAPSHOT
 */
public interface CategoryRepository extends JpaRepository<Category, String> {

    /**
     * Find the list of {@link Category Categories} corresponding to
     * the {@link com.example.productcatalog.entity.Product#id Product's ID}
     * based on the value provided <code>productId</code>
     *
     * @param productId ID of the {@link com.example.productcatalog.entity.Product}
     * @return the list of {@link Category}
     */
    List<Category> findByProducts_Id(String productId);
}
