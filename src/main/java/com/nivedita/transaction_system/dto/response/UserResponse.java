package com.nivedita.transaction_system.dto.response;

import com.nivedita.transaction_system.security.model.Role;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserResponse {

    private Long id;
    private String username;
    private String email;
    private Role role;
}
