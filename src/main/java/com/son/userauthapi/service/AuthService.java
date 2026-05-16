package com.son.userauthapi.service;

import com.son.userauthapi.dto.RegisterRequest;
import com.son.userauthapi.entity.User;
import com.son.userauthapi.repository.UserRepository;
import com.son.userauthapi.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider tokenProvider;

    public String register(RegisterRequest request) {
       if (userRepository.findByUsername(request.getUsername()).isPresent()) {
           throw new RuntimeException("Username already exists");
       }

        User user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .email(request.getEmail())
                .build();

        userRepository.save(user);
       return "Đăng kí thành công";
    }

    public String login(String username, String password){
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Tài khoản không tồn tại"));
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        return tokenProvider.generateToken(user.getUsername());
    }
}
