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

import static com.somecompany.customermatches.testobjects.TestMatches.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
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

    @MockBean
    private MatchResponseTransformer matchResponseTransformer;

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

        verify(matchResponseTransformer, never()).transform(any(), any());
    }

    @Test
    public void shouldReturnMatchesForCustomer() throws Exception {
        String customerUuid = "795ad49c-a1ad-4629-84c5-35a1c2968c74";
        Set<Match> matches = Set.of(MATCH_FEDERER_DJOKOVIC_TOMORROW);

        MatchDto expected = MatchDto.builder().matchId(MATCH_FEDERER_DJOKOVIC_TOMORROW.getMatchId()).build();

        when(licensingService.getLicensedMatches(customerUuid)).thenReturn(matches);
        when(matchResponseTransformer.transform(MATCH_FEDERER_DJOKOVIC_TOMORROW, SummaryType.AvB))
                .thenReturn(expected);

        MvcResult result = mockMvc.perform(get(String.format("/customer/%s/matches", customerUuid)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isNotEmpty())
                .andReturn();

        assertThat(jsonMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<Set<MatchDto>>(){}))
                .isEqualTo(Set.of(expected));
    }

    @Test
    public void shouldTransformMatchesWithDefaultSummaryType() throws Exception {
        String customerUuid = "795ad49c-a1ad-4629-84c5-35a1c2968c74";
        Set<Match> matches = Set.of(MATCH_FEDERER_DJOKOVIC_TOMORROW, MATCH_NADAL_MURRAY_NEXT_HOUR,
                MATCH_SHARAPOVA_WOZNIACKI_YESTERDAY);

        when(licensingService.getLicensedMatches(customerUuid)).thenReturn(matches);
        mockMvc.perform(get(String.format("/customer/%s/matches", customerUuid)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isNotEmpty());

        verify(matchResponseTransformer).transform(MATCH_FEDERER_DJOKOVIC_TOMORROW, SummaryType.AvB);
        verify(matchResponseTransformer).transform(MATCH_NADAL_MURRAY_NEXT_HOUR, SummaryType.AvB);
        verify(matchResponseTransformer).transform(MATCH_SHARAPOVA_WOZNIACKI_YESTERDAY, SummaryType.AvB);
    }

    @Test
    public void shouldTransformMatchesWithGivenSummaryType() throws Exception {
        String customerUuid = "795ad49c-a1ad-4629-84c5-35a1c2968c74";
        Set<Match> matches = Set.of(MATCH_FEDERER_DJOKOVIC_TOMORROW);

        when(licensingService.getLicensedMatches(customerUuid)).thenReturn(matches);
        mockMvc.perform(get(String.format("/customer/%s/matches?summaryType=AvBTime", customerUuid)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isNotEmpty());

        verify(matchResponseTransformer).transform(MATCH_FEDERER_DJOKOVIC_TOMORROW, SummaryType.AvBTime);
    }

    @Test
    public void shouldThrowErrorWithInvalidSummaryType() throws Exception {
        String customerUuid = "795ad49c-a1ad-4629-84c5-35a1c2968c74";
        Set<Match> matches = Set.of(MATCH_FEDERER_DJOKOVIC_TOMORROW);

        when(licensingService.getLicensedMatches(customerUuid)).thenReturn(matches);
        mockMvc.perform(get(String.format("/customer/%s/matches?summaryType=invalid", customerUuid)))
                .andExpect(status().isUnprocessableEntity());
    }
}
