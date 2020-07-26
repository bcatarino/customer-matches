package com.somecompany.customermatches.rest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static com.somecompany.customermatches.testobjects.TestMatches.MATCH_NADAL_MURRAY_NEXT_HOUR;
import static com.somecompany.customermatches.testobjects.TestMatches.MATCH_SOUSA_WAWRINKA_LAST_WEEK;
import static org.assertj.core.api.Assertions.assertThat;

public class MatchResponseTransformerTest {
    private final MatchResponseTransformer matchResponseTransformer = new MatchResponseTransformer();

    @Test
    public void shouldCreateDtoSimilarToEntity() {
        MatchDto matchDto = matchResponseTransformer.transform(MATCH_NADAL_MURRAY_NEXT_HOUR, SummaryType.AvB);
        assertThat(matchDto.getMatchId()).isEqualTo(MATCH_NADAL_MURRAY_NEXT_HOUR.getMatchId());
        assertThat(matchDto.getPlayerA()).isEqualTo(MATCH_NADAL_MURRAY_NEXT_HOUR.getPlayerA());
        assertThat(matchDto.getPlayerB()).isEqualTo(MATCH_NADAL_MURRAY_NEXT_HOUR.getPlayerB());
        assertThat(matchDto.getStartDate()).isEqualTo(MATCH_NADAL_MURRAY_NEXT_HOUR.getStartDate());
    }

    @Test
    public void shouldCreateSummaryForAvB() {
        MatchDto matchDto = matchResponseTransformer.transform(MATCH_NADAL_MURRAY_NEXT_HOUR, SummaryType.AvB);
        assertThat(matchDto.getSummary()).isEqualTo("Rafael Nadal vs Andy Murray");
    }

    @Test
    public void shouldCreateSummaryForAvBTimeInFuture() {
        MatchDto matchDto = matchResponseTransformer.transform(MATCH_NADAL_MURRAY_NEXT_HOUR, SummaryType.AvBTime);
        assertThat(matchDto.getSummary()).isEqualTo("Rafael Nadal vs Andy Murray, starts in 59 minutes");
    }

    @Test
    public void shouldCreateSummaryForAvBTimeInPast() {
        MatchDto matchDto = matchResponseTransformer.transform(MATCH_SOUSA_WAWRINKA_LAST_WEEK, SummaryType.AvBTime);
        assertThat(matchDto.getSummary()).isEqualTo("Joao Sousa vs Stan Wawrinka, started 14400 minutes ago");
    }

    @Test
    public void shouldThrowExceptionIfSummaryTypeIsNull() {
        Assertions.assertThrows(NullPointerException.class,
                () -> matchResponseTransformer.transform(MATCH_SOUSA_WAWRINKA_LAST_WEEK, null));
    }
}
