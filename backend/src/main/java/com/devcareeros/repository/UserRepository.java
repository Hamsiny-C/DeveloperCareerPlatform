package com.devcareeros.repository;

import com.devcareeros.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

/**
 * JpaRepository<User, Long> gives us, FOR FREE, without writing any SQL:
 *   save(), findById(), findAll(), deleteById(), count(), etc.
 * "User" = the entity this repository manages, "Long" = the type of its @Id.
 *
 * Spring Data JPA also lets us define custom queries just by naming a
 * method a certain way - it reads the method name and builds the SQL
 * automatically. findByEmail(...) becomes:
 *   SELECT * FROM users WHERE email = ?
 */
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
}
