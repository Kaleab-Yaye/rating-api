package com.rating.api.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rating.api.entity.Pharmacy;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class PharmacyControllerTest {

  @Autowired private MockMvc mockMvc;

  @Autowired private ObjectMapper objectMapper;

  @Test
  void createPharmacy_ValidData_ShouldReturnCreated() throws Exception {
    Pharmacy pharmacy = new Pharmacy();
    pharmacy.setName("Test Pharmacy");
    pharmacy.setStreetAddress("123 Test St");
    pharmacy.setCity("Test City");
    pharmacy.setRegion("Test Region");
    pharmacy.setPostalCode("12345");
    pharmacy.setBalance(1000L);

    mockMvc
        .perform(
            post("/api/pharmacies")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(pharmacy)))
        .andExpect(status().isCreated());
  }

  @Test
  void createPharmacy_InvalidData_ShouldReturnBadRequest() throws Exception {
    Pharmacy pharmacy = new Pharmacy();
    pharmacy.setName(""); // Invalid: empty name

    mockMvc
        .perform(
            post("/api/pharmacies")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(pharmacy)))
        .andExpect(status().isBadRequest());
  }
}
