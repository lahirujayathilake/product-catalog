package com.example.productcatalog.service;

import com.example.productcatalog.entity.Product;

import java.util.List;

/**
 * Service class definition for the {@link Product}
 *
 * @author Lahiru Jayathilake
 * @since 0.0.1-SNAPSHOT
 */
public interface IProductService {

    /**
     * Find and return the {@link Product} which Id matches
     * to the provided <code>productId</code>
     *
     * @param productId Id which requires to find the matched {@link Product}
     * @return matched {@link Product}
     */
    Product getProduct(String productId);

    /**
     * Return the list of all the {@link Product Products}
     *
     * @return list of {@link Product Products}
     */
    List<Product> getProducts(String categoryId);

    /**
     * Insert the provided <code>{@link Product}</code> if not exists
     * otherwise update the properties of it which matches to
     * {@link Product#id}
     *
     * @param product {@link Product} which need to be inserted or updated
     */
    Product createUpdateProduct(Product product);

    /**
     * Delete the {@link Product} from repository which matches to the
     * provided <code>product</code>
     *
     * @param product {@link Product} which need to be removed
     */
    void deleteProduct(Product product);
}
