package com.nivedita.transaction_system.security;

import com.nivedita.transaction_system.dto.response.ApiResponse;
import com.nivedita.transaction_system.entity.User;
import com.nivedita.transaction_system.exception.ResourceNotFoundException;
import com.nivedita.transaction_system.repository.UserRepository;
import com.nivedita.transaction_system.security.jwt.JwtUtil;
import com.nivedita.transaction_system.security.jwt.LoginRequest;
import com.nivedita.transaction_system.security.jwt.LoginResponse;
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

    // ✅ REGISTER API
    @PostMapping("/register")
    public ApiResponse<?> register(@RequestBody LoginRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            return ApiResponse.fail("Email already exists");
        }

        User user = User.builder()
                .email(request.getEmail())
                .username(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.ROLE_USER)
                .build();

        userRepository.save(user);

        return ApiResponse.success(null, "User registered successfully");
    }

    // ✅ LOGIN API (UPGRADED EXCEPTION HANDLING)
    @PostMapping("/login")
    public ApiResponse<?> login(@RequestBody LoginRequest request) {

        // ✅ USER NOT FOUND
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found with email: " + request.getEmail())
                );

        // ✅ WRONG PASSWORD
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            return ApiResponse.fail("Invalid password");
        }

        // ✅ GENERATE JWT TOKEN
        String token = jwtUtil.generateToken(user.getEmail(), user.getRole().name());


        return ApiResponse.success(
                new LoginResponse(token, user.getRole().name()),
                "Login successful"
        );
    }
}
