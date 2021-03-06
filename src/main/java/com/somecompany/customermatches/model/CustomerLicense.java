package com.somecompany.customermatches.model;

import lombok.Builder;
import lombok.Value;

import java.util.UUID;

@Value
@Builder
public class CustomerLicense {
    UUID customerId;
    UUID contentId;
    LicenseType licenseType;
}
