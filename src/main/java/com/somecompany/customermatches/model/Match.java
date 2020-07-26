package com.somecompany.customermatches.model;

import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;
import java.util.UUID;

@Value
@Builder
public class Match {
    UUID matchId;
    LocalDateTime startDate;
    String playerA;
    String playerB;
}
