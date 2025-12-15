package com.rating.api.web.controller.users.auth;

import com.rating.api.response.AuthRequest;
import com.rating.api.response.AuthResponse;
import com.rating.api.service.security.AuthService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user")
public class Login {
  private AuthService authService;

  public Login(AuthService authService) {
    this.authService = authService;
  }

  @PostMapping("/login")
  public AuthResponse login(@RequestBody AuthRequest authRequest) {
    return authService.login(authRequest);
  }
}
