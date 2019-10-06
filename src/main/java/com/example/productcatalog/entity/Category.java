package com.example.productcatalog.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.List;

/**
 * Entity class for the Category. One {@link Category} might
 * have one or many {@link Product Products}
 *
 * @author Lahiru Jayathilake
 * @since 0.0.1-SNAPSHOT
 */
@Entity
public class Category {

    @Id
    @Pattern(regexp = "^[a-zA-Z0-9-]{2,8}$")
    private String id;

    @NotNull
    private String name;

    private String description;

    @ManyToMany(
            fetch = FetchType.LAZY,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE}
    )
    @JoinTable(name = "product_category",
            joinColumns = @JoinColumn(name = "category_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id"))
    @JsonIgnore
    private List<Product> products;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    /**
     * Remove the <code>product</code> from the {@link Category#products Product list} list
     *
     * @param product {@link Product} need to be removed
     */
    public void removeProduct(Product product) {
        products.remove(product);
        product.getCategories().remove(this);
    }
}
