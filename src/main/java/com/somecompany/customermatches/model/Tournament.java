package com.somecompany.customermatches.model;

import lombok.Builder;
import lombok.Value;

import java.util.Set;
import java.util.UUID;

@Value
@Builder
public class Tournament {
    UUID tournamentId;
    Set<UUID> matches;
}
