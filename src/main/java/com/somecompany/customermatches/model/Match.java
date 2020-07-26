package com.somecompany.customermatches.model;

import lombok.Builder;
import lombok.Value;

import java.time.LocalDate;
import java.util.UUID;

@Value
@Builder
public class Match {
    UUID matchId;
    LocalDate startDate;
    String playerA;
    String playerB;
}
