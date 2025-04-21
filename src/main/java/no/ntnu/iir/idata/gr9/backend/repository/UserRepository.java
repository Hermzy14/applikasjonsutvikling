package no.ntnu.iir.idata.gr9.backend.repository;

import no.ntnu.iir.idata.gr9.backend.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Represents a user repository for managing user entities.
 */
@Repository
public interface UserRepository extends CrudRepository<User, Integer> {
    /**
     * Finds a user by its id.
     */
    User findById(int id);

    /**
     * Finds a user by its username.
     */
    User findByUsername(String username);

    /**
     * Finds a user by its email.
     */
    User findByEmail(String email);

    /**
     * Finds admin users.
     */
    Iterable<User> findByIsAdminTrue();

    /**
     * Finds non-admin users.
     */
    Iterable<User> findByIsAdminFalse();

    /**
     * Checks if a user exists with the given username.
     */
    boolean existsByUsername(String username);

    /**
     * Checks if a user exists with the given email.
     */
    boolean existsByEmail(String email);
}