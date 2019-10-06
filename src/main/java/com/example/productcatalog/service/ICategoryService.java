package com.example.productcatalog.service;

import com.example.productcatalog.entity.Category;

import java.util.List;

/**
 * Service class definition for the {@link Category}
 *
 * @author Lahiru Jayathilake
 * @since 0.0.1-SNAPSHOT
 */
public interface ICategoryService {

    /**
     * Find and return the {@link Category} which Id matches
     * to the provided <code>categoryId</code>
     *
     * @param categoryId Id which requires to find the matched {@link Category}
     * @return matched {@link Category}
     */
    Category getCategory(String categoryId);

    /**
     * Return the list of all the {@link Category Categories}
     *
     * @return list of {@link Category Categories}
     */
    List<Category> getAllCategories();

    /**
     * Insert the provided <code>{@link Category}</code> if not exists
     * otherwise update the properties of it which matches to
     * {@link Category#id}
     *
     * @param category {@link Category} which need to be inserted or updated
     */
    void createUpdateCategory(Category category);

    /**
     * Find the list of {@link Category Categories} corresponding to
     * the {@link com.example.productcatalog.entity.Product#id Product's ID}
     * based on the value provided <code>productId</code>
     *
     * @param productId ID of the {@link com.example.productcatalog.entity.Product}
     * @return the list of {@link Category}
     */
    List<Category> getCategoriesByProductId(String productId);

    /**
     * Delete the {@link Category} which maps to the
     * provided <code>categoryId</code>
     *
     * @param categoryId Id of the {@link Category} which need to be removed
     */
    void deleteCategory(String categoryId);
}
