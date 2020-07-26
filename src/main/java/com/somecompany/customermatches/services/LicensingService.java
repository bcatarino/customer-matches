package com.somecompany.customermatches.services;

import com.somecompany.customermatches.model.CustomerLicense;
import com.somecompany.customermatches.model.Match;
import com.somecompany.customermatches.repository.LicenseRepository;
import com.somecompany.customermatches.repository.MatchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LicensingService {
    private final LicenseRepository licenseRepository;
    private final MatchRepository matchRepository;

    public Set<Match> getLicensedMatches(String customerIdStr) {
        UUID customerId = UUID.fromString(customerIdStr);

        Set<UUID> matchIds = licenseRepository.getCustomerLicenses(customerId)
                .stream()
                .map(CustomerLicense::getMatchId)
                .collect(Collectors.toSet());

        return !matchIds.isEmpty() ? matchRepository.getMatchesFor(matchIds) : Set.of();
    }
}
