package com.son.userauthapi.controller;

import com.son.userauthapi.dto.AuthResponse;
import com.son.userauthapi.dto.RegisterRequest;
import com.son.userauthapi.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody Map<String, String> request) {
        String token = authService.login(request.get("username"), request.get("password"));
        return ResponseEntity.ok(new AuthResponse(token));
    }

    @GetMapping("/loginv2")
    public String login(){
        return "Đăng nhập khong thành công";
    }

    @GetMapping("/registerv2")
    public String login(){
        return "Đăng kí thành công";
    }
}
