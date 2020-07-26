package com.somecompany.customermatches.repository;

import com.somecompany.customermatches.model.CustomerLicense;

import java.util.Set;
import java.util.UUID;

public interface LicenseRepository {
    Set<CustomerLicense> getCustomerLicenses(UUID customerId);
}
