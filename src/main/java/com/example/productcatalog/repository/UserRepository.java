package com.example.productcatalog.repository;

import com.example.productcatalog.entity.User;
import org.springframework.data.repository.CrudRepository;

/**
 * JPA Repository for {@link User}
 *
 * @author Lahiru Jayathilake
 * @since 0.0.1-SNAPSHOT
 */
public interface UserRepository extends CrudRepository<User, Long> {

    /**
     * Find the User by name
     *
     * @param username username to find the User
     * @return the User with the <code>username</code>
     */
    User findByUsername(String username);
}
