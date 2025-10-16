package com.rating.api.web.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/health/v1")
public class HealthEndPoint {
  @GetMapping("/hello")
  public HealthResponse healthResponceEndPoint() {
    return new HealthResponse("ok");
  }
}
