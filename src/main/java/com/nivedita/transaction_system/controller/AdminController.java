package com.nivedita.transaction_system.controller;

import com.nivedita.transaction_system.dto.response.ApiResponse;
import com.nivedita.transaction_system.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    private final AdminService adminService;

    // ✅ GET ALL USERS
    @GetMapping("/users")
    public ApiResponse<?> getAllUsers() {
        return new ApiResponse<>(
                true,
                "Users fetched successfully",
                adminService.getAllUsers()
        );
    }

    // ✅ DELETE USER
    @DeleteMapping("/users/{id}")
    public ApiResponse<?> deleteUser(@PathVariable Long id) {
        adminService.deleteUser(id);
        return new ApiResponse<>(
                true,
                "User deleted successfully",
                null
        );
    }

    // ✅ UPDATE TRANSACTION STATUS
    @PutMapping("/transactions/{id}/status")
    public ApiResponse<?> updateTransactionStatus(
            @PathVariable Long id,
            @RequestParam String status) {

        return new ApiResponse<>(
                true,
                "Transaction status updated",
                adminService.updateTransactionStatus(id, status)
        );
    }
}
