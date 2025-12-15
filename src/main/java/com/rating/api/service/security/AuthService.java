package com.rating.api.service.security;

import com.rating.api.response.AuthRequest;
import com.rating.api.response.AuthResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
  private final AuthenticationManager authenticationManager;
  private final JwtService jwtService;

  public AuthService(AuthenticationManager authenticationManager, JwtService jwtService) {
    this.authenticationManager = authenticationManager;
    this.jwtService = jwtService;
  }

  public AuthResponse login(AuthRequest authRequest) {

    // kinda boiler plate just know this is the core of autentication before token is give
    // returen fully aoutenticated object and puts is in to the thread local
    Authentication authentication =
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(authRequest.email(), authRequest.password()));

    // extract userDetials, since that is the generateToken takes as an arrguement
    UserDetails userDetails = (UserDetails) authentication.getPrincipal();

    return (new AuthResponse(jwtService.generateToken(userDetails)));
  }
}
