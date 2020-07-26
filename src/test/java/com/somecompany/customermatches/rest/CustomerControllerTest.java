package com.somecompany.customermatches.rest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CustomerControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void shouldReturnEmptyListIfNoMatches() throws Exception {
        mockMvc.perform(get("/customer/c5d932e1-9a4a-4848-b4d9-0e8331a8f497/matches"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isEmpty());
    }
}
