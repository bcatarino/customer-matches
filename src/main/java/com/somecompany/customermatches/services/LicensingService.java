package com.somecompany.customermatches.services;

import com.somecompany.customermatches.model.CustomerLicense;
import com.somecompany.customermatches.model.LicenseType;
import com.somecompany.customermatches.model.Match;
import com.somecompany.customermatches.repository.LicenseRepository;
import com.somecompany.customermatches.repository.MatchRepository;
import com.somecompany.customermatches.repository.TournamentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class LicensingService {
    private final LicenseRepository licenseRepository;
    private final MatchRepository matchRepository;
    private final TournamentRepository tournamentRepository;

    public Set<Match> getLicensedMatches(String customerIdStr) {
        UUID customerId = UUID.fromString(customerIdStr);

        Set<CustomerLicense> customerLicenses = licenseRepository.getCustomerLicenses(customerId);
        if (customerLicenses.isEmpty()) {
            return Set.of();
        }

        Stream<UUID> filteredMatchIds = filterByType(customerLicenses, LicenseType.MATCH).stream();
        Stream<UUID> tournamentMatchIds = getMatchIdsForCustomerTournaments(customerLicenses).stream();
        Set<UUID> matchIdsToFetch = Stream.concat(filteredMatchIds, tournamentMatchIds).collect(Collectors.toSet());

        return !matchIdsToFetch.isEmpty() ? matchRepository.getMatchesFor(matchIdsToFetch) : Set.of();
    }

    private Set<UUID> getMatchIdsForCustomerTournaments(Set<CustomerLicense> customerLicenses) {
        Set<UUID> tournamentIds = filterByType(customerLicenses, LicenseType.TOURNAMENT);
        return !tournamentIds.isEmpty()
                ? tournamentRepository.getMatcheIdsForTournaments(tournamentIds)
                : Set.of();
    }

    private Set<UUID> filterByType(Set<CustomerLicense> customerLicenses, LicenseType licenseType) {
        return customerLicenses.stream()
                .filter(license -> license.getLicenseType() == licenseType)
                .map(CustomerLicense::getContentId)
                .collect(Collectors.toSet());
    }
}
