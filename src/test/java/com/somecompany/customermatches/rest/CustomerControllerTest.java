package com.somecompany.customermatches.rest;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.somecompany.customermatches.model.Match;
import com.somecompany.customermatches.services.LicensingService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Set;

import static com.somecompany.customermatches.testobjects.TestMatches.MATCH_FEDERER_DJOKOVIC_TOMORROW;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CustomerControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LicensingService licensingService;

    @Autowired
    private ObjectMapper jsonMapper;

    @Test
    public void shouldReturn404IfNoCustomerProvided() throws Exception {
        mockMvc.perform(get("/customer//matches"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldReturnEmptyListIfNoMatches() throws Exception {
        String customerUuid = "c5d932e1-9a4a-4848-b4d9-0e8331a8f497";
        when(licensingService.getLicensedMatches(customerUuid)).thenReturn(Set.of());
        mockMvc.perform(get(String.format("/customer/%s/matches", customerUuid)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    public void shouldReturnSingleMatchesForCustomer() throws Exception {
        String customerUuid = "795ad49c-a1ad-4629-84c5-35a1c2968c74";
        Set<Match> matches = Set.of(MATCH_FEDERER_DJOKOVIC_TOMORROW);

        when(licensingService.getLicensedMatches(customerUuid)).thenReturn(matches);
        MvcResult result = mockMvc.perform(get(String.format("/customer/%s/matches", customerUuid)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isNotEmpty())
                .andReturn();

        assertThat(jsonMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<Set<Match>>(){}))
                .isEqualTo(matches);
    }
}
