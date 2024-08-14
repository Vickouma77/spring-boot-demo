package com.secure_auth.auth_service.services;

import com.secure_auth.auth_service.dto.LoginDto;

public interface AuthService {

    String login(LoginDto login);
}
