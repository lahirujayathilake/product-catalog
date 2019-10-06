package com.example.productcatalog.repository;

import com.example.productcatalog.entity.Role;
import org.springframework.data.repository.CrudRepository;

/**
 * JPA Repository for the {@link Role}
 *
 * @author Lahiru Jayathilake
 * @since 0.0.1-SNAPSHOT
 */
public interface RoleRepository extends CrudRepository<Role, Long> {
}
