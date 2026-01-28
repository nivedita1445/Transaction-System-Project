package com.nivedita.transaction_system.repository;

import com.nivedita.transaction_system.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    // ✅ JWT LOGIN SUPPORT
    Optional<User> findByEmail(String email);

    // ✅ REGISTER VALIDATION
    boolean existsByEmail(String email);
}
