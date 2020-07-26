package com.somecompany.customermatches.repository;

import java.util.Set;
import java.util.UUID;

public interface TournamentRepository {
    Set<UUID> getMatcheIdsForTournaments(Set<UUID> tournamentIds);
}
