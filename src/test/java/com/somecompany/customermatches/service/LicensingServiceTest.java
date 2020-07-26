package com.somecompany.customermatches.service;

import com.somecompany.customermatches.model.CustomerLicense;
import com.somecompany.customermatches.model.Match;
import com.somecompany.customermatches.repository.LicenseRepository;
import com.somecompany.customermatches.repository.MatchRepository;
import com.somecompany.customermatches.services.LicensingService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.somecompany.customermatches.testobjects.TestMatches.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.never;

public class LicensingServiceTest {
    @Mock
    private MatchRepository matchRepository;

    @Mock
    private LicenseRepository licenseRepository;

    @InjectMocks
    private LicensingService licensingService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldGetMatchesForCustomer() {
        UUID customerId = UUID.fromString("01ce3bfc-061d-4daf-8532-73a57d92ee62");
        Set<CustomerLicense> customerLicenses = Set.of(
                new CustomerLicense(customerId, MATCH_FEDERER_DJOKOVIC_TOMORROW.getMatchId()));
        Set<Match> customerMatches = Set.of(MATCH_FEDERER_DJOKOVIC_TOMORROW);
        Set<UUID> matchIds = Set.of(MATCH_FEDERER_DJOKOVIC_TOMORROW.getMatchId());

        when(licenseRepository.getCustomerLicenses(customerId)).thenReturn(customerLicenses);
        when(matchRepository.getMatchesFor(matchIds)).thenReturn(customerMatches);

        Set<Match> licensedMatches = licensingService.getLicensedMatches(customerId.toString());
        assertThat(licensedMatches).isEqualTo(customerMatches);
    }

    @Test
    public void shouldGetEmptyListIfNoLicenseForCustomer() {
        UUID customerId = UUID.fromString("01ce3bfc-061d-4daf-8532-73a57d92ee62");

        when(licenseRepository.getCustomerLicenses(customerId)).thenReturn(Set.of());

        Set<Match> licensedMatches = licensingService.getLicensedMatches(customerId.toString());
        assertThat(licensedMatches).isEqualTo(Set.of());

        verify(matchRepository, never()).getMatchesFor(any());
    }

    @Test
    public void shouldGetAllTheMatchesForCustomer() {
        UUID customerId = UUID.fromString("01ce3bfc-061d-4daf-8532-73a57d92ee62");
        Set<CustomerLicense> customerLicenses = Set.of(
                new CustomerLicense(customerId, MATCH_FEDERER_DJOKOVIC_TOMORROW.getMatchId()),
                new CustomerLicense(customerId, MATCH_SERENA_VENUS_NEXT_WEEK.getMatchId()),
                new CustomerLicense(customerId, MATCH_NADAL_MURRAY_NEXT_HOUR.getMatchId()));
        Set<Match> customerMatches = Set.of(
                MATCH_FEDERER_DJOKOVIC_TOMORROW, MATCH_SERENA_VENUS_NEXT_WEEK, MATCH_NADAL_MURRAY_NEXT_HOUR);
        Set<UUID> matchIds = customerMatches.stream().map(Match::getMatchId).collect(Collectors.toSet());

        when(licenseRepository.getCustomerLicenses(customerId)).thenReturn(customerLicenses);
        when(matchRepository.getMatchesFor(matchIds)).thenReturn(customerMatches);

        Set<Match> licensedMatches = licensingService.getLicensedMatches(customerId.toString());
        assertThat(licensedMatches).isEqualTo(customerMatches);
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
