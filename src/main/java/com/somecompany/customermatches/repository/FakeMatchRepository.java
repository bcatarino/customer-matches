package com.somecompany.customermatches.repository;

import com.somecompany.customermatches.model.Match;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public class FakeMatchRepository implements MatchRepository {
    private final Map<UUID, Match> matches;

    public FakeMatchRepository() {
        Match match1 = Match.builder()
                .matchId(UUID.fromString("079415d6-9acb-40f3-ab2f-03f4db7001e2"))
                .startDate(LocalDateTime.now().plusDays(1))
                .playerA("Roger Federer")
                .playerB("Novak Djokovic")
                .build();

        Match match2 = Match.builder()
                .matchId(UUID.fromString("1a274af9-b447-4697-a876-6a0717efcb90"))
                .startDate(LocalDateTime.now().plusHours(1))
                .playerA("Rafael Nadal")
                .playerB("Andy Murray")
                .build();

        Match match3 = Match.builder()
                .matchId(UUID.fromString("4b5ce66d-caac-41cf-a9c6-7ff0a1f9a3fd"))
                .startDate(LocalDateTime.now().plusDays(8))
                .playerA("Serena Williams")
                .playerB("Venus Williams")
                .build();

        Match match4 = Match.builder()
                .matchId(UUID.fromString("b8d2c400-7a85-4d9b-bd45-efbc3b7cd4d9"))
                .startDate(LocalDateTime.now().minusDays(1))
                .playerA("Maria Sharapova")
                .playerB("Caroline Wozniacki")
                .build();

        Match match5 = Match.builder()
                .matchId(UUID.fromString("2f4c767c-090d-4ec7-acaa-8545665b3144"))
                .startDate(LocalDateTime.now().minusDays(10))
                .playerA("Joao Sousa")
                .playerB("Stan Wawrinka")
                .build();


        matches = Map.of(UUID.fromString("079415d6-9acb-40f3-ab2f-03f4db7001e2"), match1,
                UUID.fromString("1a274af9-b447-4697-a876-6a0717efcb90"), match2,
                UUID.fromString("4b5ce66d-caac-41cf-a9c6-7ff0a1f9a3fd"), match3,
                UUID.fromString("b8d2c400-7a85-4d9b-bd45-efbc3b7cd4d9"), match4,
                UUID.fromString("2f4c767c-090d-4ec7-acaa-8545665b3144"), match5);
    }

    @Override
    public Set<Match> getMatchesFor(Set<UUID> matchIds) {
        return matchIds.stream()
                .map(matches::get)
                .collect(Collectors.toSet());
    }
}
