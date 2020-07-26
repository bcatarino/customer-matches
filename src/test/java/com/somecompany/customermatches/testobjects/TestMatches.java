package com.somecompany.customermatches.testobjects;

import com.somecompany.customermatches.model.Match;
import com.somecompany.customermatches.model.Tournament;

import java.time.LocalDateTime;
import java.util.Set;
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

    public static final Tournament US_OPEN = Tournament.builder()
            .tournamentId(UUID.fromString("bb3ddc09-c933-4cac-b964-08845380ae98"))
            .matches(Set.of(MATCH_NADAL_MURRAY_NEXT_HOUR.getMatchId(),
                    MATCH_FEDERER_DJOKOVIC_TOMORROW.getMatchId(),
                    MATCH_SERENA_VENUS_NEXT_WEEK.getMatchId()))
            .build();

    public static final Match MATCH_SHARAPOVA_WOZNIACKI_YESTERDAY = Match.builder()
            .matchId(UUID.fromString("b8d2c400-7a85-4d9b-bd45-efbc3b7cd4d9"))
            .startDate(LocalDateTime.now().minusDays(1))
            .playerA("Maria Sharapova")
            .playerB("Caroline Wozniacki")
            .build();

    public static final Match MATCH_SOUSA_WAWRINKA_LAST_WEEK = Match.builder()
            .matchId(UUID.fromString("2f4c767c-090d-4ec7-acaa-8545665b3144"))
            .startDate(LocalDateTime.now().minusDays(10))
            .playerA("Joao Sousa")
            .playerB("Stan Wawrinka")
            .build();

    public static final Tournament WIMBLEDON = Tournament.builder()
            .tournamentId(UUID.fromString("d1948485-42fd-4267-bbe2-0479d3ef4f1d"))
            .matches(Set.of(MATCH_SHARAPOVA_WOZNIACKI_YESTERDAY.getMatchId(),
                    MATCH_SOUSA_WAWRINKA_LAST_WEEK.getMatchId()))
            .build();

}
