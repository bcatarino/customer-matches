package com.somecompany.customermatches.rest;

import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.Value;

import java.time.LocalDateTime;
import java.util.UUID;

@Value
@Builder
@RequiredArgsConstructor
public class MatchDto {
    UUID matchId;
    LocalDateTime startDate;
    String playerA;
    String playerB;
    String summary;
}
