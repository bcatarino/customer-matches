package com.somecompany.customermatches.model;

import lombok.Value;

import java.util.UUID;

@Value
public class CustomerLicense {
    UUID customerId;
    UUID matchId;
}
