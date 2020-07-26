package com.somecompany.customermatches.repository;

import com.somecompany.customermatches.model.Match;

import java.util.Set;
import java.util.UUID;

public interface MatchRepository {
    Set<Match> getMatchesFor(Set<UUID> matchIds);
}
