package com.somecompany.customermatches.service;

import com.somecompany.customermatches.model.CustomerLicense;
import com.somecompany.customermatches.model.LicenseType;
import com.somecompany.customermatches.model.Match;
import com.somecompany.customermatches.repository.LicenseRepository;
import com.somecompany.customermatches.repository.MatchRepository;
import com.somecompany.customermatches.repository.TournamentRepository;
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
    private TournamentRepository tournamentRepository;

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

        Set<CustomerLicense> customerLicenses = Set.of(CustomerLicense.builder()
                .customerId(customerId)
                .contentId(MATCH_FEDERER_DJOKOVIC_TOMORROW.getMatchId())
                .licenseType(LicenseType.MATCH)
                .build());

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
        verify(tournamentRepository, never()).getMatcheIdsForTournaments(any());
    }

    @Test
    public void shouldGetAllTheMatchesForCustomer() {
        UUID customerId = UUID.fromString("01ce3bfc-061d-4daf-8532-73a57d92ee62");

        Set<CustomerLicense> customerLicenses = Set.of(
                CustomerLicense.builder()
                        .customerId(customerId)
                        .contentId(MATCH_FEDERER_DJOKOVIC_TOMORROW.getMatchId())
                        .licenseType(LicenseType.MATCH)
                        .build(),
                CustomerLicense.builder()
                        .customerId(customerId)
                        .contentId(MATCH_SERENA_VENUS_NEXT_WEEK.getMatchId())
                        .licenseType(LicenseType.MATCH)
                        .build(),
                CustomerLicense.builder()
                        .customerId(customerId)
                        .contentId(MATCH_NADAL_MURRAY_NEXT_HOUR.getMatchId())
                        .licenseType(LicenseType.MATCH)
                        .build());

        Set<Match> customerMatches = Set.of(
                MATCH_FEDERER_DJOKOVIC_TOMORROW, MATCH_SERENA_VENUS_NEXT_WEEK, MATCH_NADAL_MURRAY_NEXT_HOUR);
        Set<UUID> matchIds = customerMatches.stream().map(Match::getMatchId).collect(Collectors.toSet());

        when(licenseRepository.getCustomerLicenses(customerId)).thenReturn(customerLicenses);
        when(matchRepository.getMatchesFor(matchIds)).thenReturn(customerMatches);

        Set<Match> licensedMatches = licensingService.getLicensedMatches(customerId.toString());
        assertThat(licensedMatches).isEqualTo(customerMatches);

        verify(tournamentRepository, never()).getMatcheIdsForTournaments(any());
    }

    @Test
    public void shouldGetMatchesForCustomerWithTournament() {
        UUID customerId = UUID.fromString("01ce3bfc-061d-4daf-8532-73a57d92ee62");
        UUID tournamendId = US_OPEN.getTournamentId();

        Set<CustomerLicense> customerLicenses = Set.of(CustomerLicense.builder()
                .customerId(customerId)
                .contentId(tournamendId)
                .licenseType(LicenseType.TOURNAMENT)
                .build());

        Set<Match> customerMatches = Set.of(MATCH_FEDERER_DJOKOVIC_TOMORROW,
                MATCH_NADAL_MURRAY_NEXT_HOUR,
                MATCH_SERENA_VENUS_NEXT_WEEK);
        Set<UUID> matchIds = customerMatches.stream().map(Match::getMatchId).collect(Collectors.toSet());

        when(licenseRepository.getCustomerLicenses(customerId)).thenReturn(customerLicenses);
        when(tournamentRepository.getMatcheIdsForTournaments(Set.of(tournamendId))).thenReturn(matchIds);
        when(matchRepository.getMatchesFor(matchIds)).thenReturn(customerMatches);

        Set<Match> licensedMatches = licensingService.getLicensedMatches(customerId.toString());
        assertThat(licensedMatches).isEqualTo(customerMatches);
    }

    @Test
    public void shouldNotReturnDuplicatesIfCustomerHasBothTournamentAndMatch() {
        UUID customerId = UUID.fromString("01ce3bfc-061d-4daf-8532-73a57d92ee62");
        UUID tournamendId = WIMBLEDON.getTournamentId();
        UUID singularMatchId = MATCH_SOUSA_WAWRINKA_LAST_WEEK.getMatchId();

        Set<CustomerLicense> customerLicenses = Set.of(CustomerLicense.builder()
                        .customerId(customerId)
                        .contentId(tournamendId)
                        .licenseType(LicenseType.TOURNAMENT)
                        .build(),
                CustomerLicense.builder()
                        .customerId(customerId)
                        .contentId(singularMatchId)
                        .licenseType(LicenseType.MATCH)
                        .build());

        Set<Match> customerMatches = Set.of(MATCH_SHARAPOVA_WOZNIACKI_YESTERDAY,
                MATCH_SOUSA_WAWRINKA_LAST_WEEK);
        Set<UUID> matchIds = customerMatches.stream().map(Match::getMatchId).collect(Collectors.toSet());

        when(licenseRepository.getCustomerLicenses(customerId)).thenReturn(customerLicenses);
        when(tournamentRepository.getMatcheIdsForTournaments(Set.of(tournamendId))).thenReturn(matchIds);
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
