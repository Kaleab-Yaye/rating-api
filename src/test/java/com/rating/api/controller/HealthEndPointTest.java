package com.rating.api.controller;


import com.rating.api.web.controller.HealthEndPoint;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(HealthEndPoint.class)
public class HealthEndPointTest {
    @Autowired MockMvc mockMvc;

    @Test
    public void healthEndPointResponseTest() throws Exception{
        mockMvc.perform(get("/api/health/v1/hello")).andExpect(jsonPath("$.status").value("ok"));

    }

    @Test
    public void ealthCheckReturns406ForUnsupportedMedea() throws Exception{
        mockMvc.perform((get("/api/health/v1/hello").accept(MediaType.APPLICATION_ATOM_XML))).andExpect(status().isNotAcceptable());
    }
}