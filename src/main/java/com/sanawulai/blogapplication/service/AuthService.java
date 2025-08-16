package com.sanawulai.blogapplication.service;

import com.sanawulai.blogapplication.payload.LoginDto;
import com.sanawulai.blogapplication.payload.RegisterDto;

public interface AuthService {

    String login(LoginDto loginDto);

    String register(RegisterDto registerDto);
}
