package com.somecompany.customermatches.repository;

import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public class FakeTournamentRepository implements TournamentRepository {
    private final Map<UUID, Set<UUID>> matchesPerTournament;

    public FakeTournamentRepository() {
        Set<UUID> set1 = Set.of(UUID.fromString("079415d6-9acb-40f3-ab2f-03f4db7001e2"),
                UUID.fromString("1a274af9-b447-4697-a876-6a0717efcb90"),
                UUID.fromString("4b5ce66d-caac-41cf-a9c6-7ff0a1f9a3fd"));

        Set<UUID> set2 = Set.of(UUID.fromString("b8d2c400-7a85-4d9b-bd45-efbc3b7cd4d9"),
                UUID.fromString("2f4c767c-090d-4ec7-acaa-8545665b3144"));

        matchesPerTournament = Map.of(UUID.fromString("22978b18-a69e-44f9-a58c-1c87a07a33ad"), set1,
                UUID.fromString("8b08c228-a2c0-4bf0-8956-fea4a4ed52c6"), set2);
    }

    @Override
    public Set<UUID> getMatchesIdsForTournaments(Set<UUID> tournamentIds) {
        return tournamentIds.stream()
                .flatMap(tournamentId -> matchesPerTournament.get(tournamentId).stream())
                .collect(Collectors.toSet());
    }
}
