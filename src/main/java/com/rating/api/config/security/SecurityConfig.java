package com.rating.api.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration // i made sure spring boot looks at this class at start up
public class SecurityConfig {
  @Bean // the object returned form this methode will becomes a bean manged by spring
  public SecurityFilterChain securityFilterChain(
      HttpSecurity httpSecurity, JwtAuthenticationFilter jwtAuthenticationFilter)
      throws Exception { // HttpSecuirty Object lets us build the Filter chain in builder patter
    httpSecurity
        .sessionManagement(
            session ->
                session.sessionCreationPolicy(
                    SessionCreationPolicy
                        .STATELESS)) // this puts in to the list that we dont need cookies
        .csrf(
            csrf ->
                csrf.disable()) // since no cockies is bieing used no need fo tthe csrf protection
        // needed
        .authorizeHttpRequests(
            auth ->
                auth // this is how we build the requist chain to make sure that some parts are
                    // allow all some parts are allow all
                    .requestMatchers(
                        "api/health/v1/hello",
                        "/api/v1/register/pharmacists",
                        "/error",
                        "/api/v1/user/login")
                    .permitAll()
                    .requestMatchers("/api/v1/pharmacist/add_new_pharmacist_to_pharmacy")
                    .hasRole("PHARMACIST")
                    .requestMatchers("/api/v1/pharmacist/add_new_pharmacist_to_pharmacy")
                    .hasRole("ADMIN")
                    .anyRequest()
                    .authenticated())
        .addFilterBefore(
            jwtAuthenticationFilter,
            UsernamePasswordAuthenticationFilter
                .class); // this addes our new secuiryt filter before the secuiryt filter that we
    // mentioned
    return httpSecurity
        .build(); // the list is build and tomcat can now iterate over eatch filters to see what to
    // do with teh request
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public AuthenticationManager authenticationManager(
      AuthenticationConfiguration authenticationConfiguration) throws Exception {
    return authenticationConfiguration.getAuthenticationManager();
  }
}
