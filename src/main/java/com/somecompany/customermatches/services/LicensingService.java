package com.somecompany.customermatches.services;

import com.somecompany.customermatches.model.Match;
import com.somecompany.customermatches.repository.MatchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LicensingService {
    private final MatchRepository matchRepository;

    public List<Match> getLicensedMatches(String customerIdStr) {
        UUID customerId = UUID.fromString(customerIdStr);
        return matchRepository.getMatchesByCustomerId(customerId);
    }
}
