package com.interview.notes.y2024.june.test1.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ProductControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testEndpointWithoutTypeParameter() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/products"))
               .andExpect(MockMvcResultMatchers.status().isOk())
               .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(10)) // Adjust the expected length based on your test case
               .andExpect(MockMvcResultMatchers.jsonPath("$[0].product_uid").value(1081459))
               .andExpect(MockMvcResultMatchers.jsonPath("$[0].product_type").value("BASIC"))
               .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("Sainsbury's Coleslaw, Taste the Difference 300g"))
               .andExpect(MockMvcResultMatchers.jsonPath("$[0].full_url").value("https://www.sainsburys.co.uk/gol-ui/product/sainsburys-coleslaw--taste-the-difference-300g"))
               .andExpect(MockMvcResultMatchers.jsonPath("$[0].unit_price").value(0.42))
               .andExpect(MockMvcResultMatchers.jsonPath("$[0].unit_price_measure").value("g"))
               .andExpect(MockMvcResultMatchers.jsonPath("$[0].unit_price_measure_amount").value(100));
        // Add more assertions for other products as per your test case
    }

    @Test
    public void testEndpointWithTypeParameter() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/products?type=BASIC2"))
               .andExpect(MockMvcResultMatchers.status().isOk())
               .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(2)) // Adjust the expected length based on your test case
               .andExpect(MockMvcResultMatchers.jsonPath("$[0].product_uid").value(7554911))
               .andExpect(MockMvcResultMatchers.jsonPath("$[0].product_type").value("BASIC2"))
               .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("Sainsbury's Wafer Thin Air Dried British Ham Slices, Taste the Difference 120g"))
               .andExpect(MockMvcResultMatchers.jsonPath("$[0].full_url").value("https://www.sainsburys.co.uk/gol-ui/product/sainsburys-air-dried-lean-ham-finely-sliced--taste-the-difference-120g"))
               .andExpect(MockMvcResultMatchers.jsonPath("$[0].unit_price").value(1.88))
               .andExpect(MockMvcResultMatchers.jsonPath("$[0].unit_price_measure").value("g"))
               .andExpect(MockMvcResultMatchers.jsonPath("$[0].unit_price_measure_amount").value(100));
        // Add more assertions for other products as per your test case
    }

    @Test
    public void testEndpointWithInvalidTypeParameter() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/products?type=INVALID_TYPE"))
               .andExpect(MockMvcResultMatchers.status().isOk())
               .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(0)); // Expecting an empty array
    }
}
