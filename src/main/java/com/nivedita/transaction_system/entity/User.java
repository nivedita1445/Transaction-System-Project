package com.nivedita.transaction_system.entity;

import com.nivedita.transaction_system.security.model.Role;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 👤 Username
    @Column(nullable = false)
    private String username;

    // 📧 Email (used for login)
    @Column(unique = true, nullable = false)
    private String email;

    // 🔐 Password (encrypted)
    @Column(nullable = false)
    private String password;

    // 👑 Role (ADMIN / USER)
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;
}
