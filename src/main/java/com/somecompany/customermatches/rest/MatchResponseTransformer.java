package com.somecompany.customermatches.rest;

import com.somecompany.customermatches.model.Match;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;

@Component
public class MatchResponseTransformer {
    public MatchDto transform(Match match, SummaryType summaryType) {
        return MatchDto.builder()
                .matchId(match.getMatchId())
                .startDate(match.getStartDate())
                .playerA(match.getPlayerA())
                .playerB(match.getPlayerB())
                .summary(getSummary(match, summaryType))
                .build();
    }

    private String getSummary(Match match, SummaryType summaryType) {
        switch (summaryType) {
            case AvBTime:
                return String.format("%s vs %s, %s",
                        match.getPlayerA(), match.getPlayerB(), getTimedSummary(match.getStartDate()));
            case AvB:
            default:
                return String.format("%s vs %s", match.getPlayerA(), match.getPlayerB());
        }
    }

    private String getTimedSummary(LocalDateTime matchTime) {
        LocalDateTime now = LocalDateTime.now();
        long timeToGame = Duration.between(now, matchTime).toMinutes();

        return timeToGame > 0
                ? String.format("starts in %d minutes", timeToGame)
                : String.format("started %d minutes ago", Math.abs(timeToGame));
    }
}
