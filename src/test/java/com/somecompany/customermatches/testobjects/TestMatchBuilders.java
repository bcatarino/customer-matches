package com.somecompany.customermatches.testobjects;

import com.somecompany.customermatches.model.Match;

import java.time.LocalDateTime;

public class TestMatchBuilders {
    public static final Match.MatchBuilder MATCH_FEDERER_DJOKOVIC_TOMORROW = Match.builder()
            .startDate(LocalDateTime.now().plusDays(1))
            .playerA("Roger Federer")
            .playerB("Novak Djokovic");
}
