package com.nivedita.transaction_system.security.jwt;

import lombok.Data;

@Data
public class LoginRequest {
    private String email;
    private String password;
}
