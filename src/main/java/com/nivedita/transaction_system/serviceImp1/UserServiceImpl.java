package com.nivedita.transaction_system.serviceImp1;

import com.nivedita.transaction_system.dto.request.UserRequest;
import com.nivedita.transaction_system.dto.response.UserResponse;
import com.nivedita.transaction_system.entity.User;
import com.nivedita.transaction_system.exception.ResourceNotFoundException;
import com.nivedita.transaction_system.repository.UserRepository;
import com.nivedita.transaction_system.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public UserResponse getProfile() {

        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        return new UserResponse(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getRole()
        );
    }

    @Override
    public UserResponse updateProfile(UserRequest request) {

        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());

        User updated = userRepository.save(user);

        return new UserResponse(
                updated.getId(),
                updated.getUsername(),
                updated.getEmail(),
                updated.getRole()
        );
    }
}
