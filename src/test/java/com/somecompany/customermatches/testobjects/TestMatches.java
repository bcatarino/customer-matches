package com.somecompany.customermatches.testobjects;

import com.somecompany.customermatches.model.Match;

import java.time.LocalDateTime;
import java.util.UUID;

public class TestMatches {
    public static final Match MATCH_FEDERER_DJOKOVIC_TOMORROW = Match.builder()
            .matchId(UUID.fromString("079415d6-9acb-40f3-ab2f-03f4db7001e2"))
            .startDate(LocalDateTime.now().plusDays(1))
            .playerA("Roger Federer")
            .playerB("Novak Djokovic")
            .build();

    public static final Match MATCH_NADAL_MURRAY_NEXT_HOUR = Match.builder()
            .matchId(UUID.fromString("1a274af9-b447-4697-a876-6a0717efcb90"))
            .startDate(LocalDateTime.now().plusHours(1))
            .playerA("Rafael Nadal")
            .playerB("Andy Murray")
            .build();

    public static final Match MATCH_SERENA_VENUS_NEXT_WEEK = Match.builder()
            .matchId(UUID.fromString("4b5ce66d-caac-41cf-a9c6-7ff0a1f9a3fd"))
            .startDate(LocalDateTime.now().plusDays(8))
            .playerA("Serena Williams")
            .playerB("Venus Williams")
            .build();
}
