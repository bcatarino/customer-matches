package com.somecompany.customermatches.repository;

import com.somecompany.customermatches.model.Match;

import java.util.List;
import java.util.UUID;

public interface MatchRepository {
    List<Match> getMatchesByCustomerId(UUID customerId);
}
