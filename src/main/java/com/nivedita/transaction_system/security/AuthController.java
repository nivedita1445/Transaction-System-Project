package com.nivedita.transaction_system.security;

import com.nivedita.transaction_system.dto.response.ApiResponse;
import com.nivedita.transaction_system.entity.User;
import com.nivedita.transaction_system.repository.UserRepository;
import com.nivedita.transaction_system.security.jwt.*;
import com.nivedita.transaction_system.security.model.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @PostMapping("/register")
    public ApiResponse<?> register(@RequestBody LoginRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            return new ApiResponse<>(false, "Email already exists", null);
        }

        User user = User.builder()
                .email(request.getEmail())
                .username(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.ROLE_USER)
                .build();

        userRepository.save(user);

        return new ApiResponse<>(true, "User registered successfully", null);
    }

    @PostMapping("/login")
    public ApiResponse<?> login(@RequestBody LoginRequest request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Invalid credentials"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        String token = jwtUtil.generateToken(user.getEmail());

        return new ApiResponse<>(true, "Login successful",
                new LoginResponse(token, user.getRole().name()));
    }
}
