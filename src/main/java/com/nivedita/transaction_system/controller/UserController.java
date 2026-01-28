package com.nivedita.transaction_system.controller;

import com.nivedita.transaction_system.dto.request.UserRequest;
import com.nivedita.transaction_system.dto.response.ApiResponse;
import com.nivedita.transaction_system.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
@PreAuthorize("hasRole('USER')")
public class UserController {

    private final UserService userService;

    // ✅ GET USER PROFILE
    @GetMapping("/profile")
    public ApiResponse<?> getProfile() {
        return new ApiResponse<>(
                true,
                "User profile fetched successfully",
                userService.getProfile()
        );
    }

    // ✅ UPDATE USER PROFILE
    @PutMapping("/profile")
    public ApiResponse<?> updateProfile(@RequestBody UserRequest request) {
        return new ApiResponse<>(
                true,
                "User profile updated successfully",
                userService.updateProfile(request)
        );
    }
}
