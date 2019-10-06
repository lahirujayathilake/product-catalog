package com.example.productcatalog.entity;

import javax.persistence.*;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.List;

/**
 * Entity class for the Product. One {@link Product} might
 * belong to one or many {@link Category Categories}
 *
 * @author Lahiru Jayathilake
 * @since 0.0.1-SNAPSHOT
 */
@Entity
public class Product {

    @Id
    @Pattern(regexp = "^[a-zA-Z0-9-]{2,8}$")
    private String id;

    @NotNull
    private String name;

    @Column(precision = 10, scale = 2)
    @DecimalMax("99999999.99")
    @DecimalMin("0.01")
    private double unitPrice;

    @ManyToMany(
            fetch = FetchType.LAZY,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE},
            mappedBy = "products"
    )
    private List<Category> categories;

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

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }
}
