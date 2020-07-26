package com.somecompany.customermatches.service;

import com.somecompany.customermatches.model.Match;
import com.somecompany.customermatches.repository.MatchRepository;
import com.somecompany.customermatches.services.LicensingService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.UUID;

import static com.somecompany.customermatches.testobjects.TestMatchBuilders.MATCH_FEDERER_DJOKOVIC_TOMORROW;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

public class LicensingServiceTest {
    @Mock
    private MatchRepository matchRepository;

    @InjectMocks
    private LicensingService licensingService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldGetMatchesForCustomer() {
        UUID customerId = UUID.fromString("01ce3bfc-061d-4daf-8532-73a57d92ee62");
        List<Match> expected = List.of(MATCH_FEDERER_DJOKOVIC_TOMORROW.matchId(customerId).build());

        when(matchRepository.getMatchesByCustomerId(customerId)).thenReturn(expected);

        List<Match> licensedMatches = licensingService.getLicensedMatches(customerId.toString());
        assertThat(licensedMatches).isEqualTo(expected);
    }

    @Test
    public void shouldThrowExceptionIfCustomerIdIsNotValidUuid() {
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> licensingService.getLicensedMatches("blahblah"));
    }

    @Test
    public void shouldThrowExceptionWhenNoCustomerIdProvided() {
        Assertions.assertThrows(NullPointerException.class,
                () -> licensingService.getLicensedMatches(null));
    }
}
