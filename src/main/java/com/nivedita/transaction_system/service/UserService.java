package com.nivedita.transaction_system.service;

import com.nivedita.transaction_system.dto.request.UserRequest;
import com.nivedita.transaction_system.dto.response.UserResponse;

public interface UserService {

    UserResponse getProfile();

    UserResponse updateProfile(UserRequest request);
}
