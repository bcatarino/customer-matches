package com.somecompany.customermatches.repository;

import com.somecompany.customermatches.model.CustomerLicense;
import com.somecompany.customermatches.model.LicenseType;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Set;
import java.util.UUID;

@Repository
public class FakeLicenseRepository implements LicenseRepository {
    private final Map<UUID, Set<CustomerLicense>> licensesPerUser;

    public FakeLicenseRepository() {
        Set<CustomerLicense> set1 = Set.of(CustomerLicense.builder()
                        .contentId(UUID.fromString("22978b18-a69e-44f9-a58c-1c87a07a33ad"))
                        .licenseType(LicenseType.TOURNAMENT)
                        .customerId(UUID.fromString("f411c4e7-1278-4fa3-8ab9-e8c264e12952"))
                        .build(),
                CustomerLicense.builder()
                        .contentId(UUID.fromString("2f4c767c-090d-4ec7-acaa-8545665b3144"))
                        .licenseType(LicenseType.MATCH)
                        .customerId(UUID.fromString("f411c4e7-1278-4fa3-8ab9-e8c264e12952"))
                        .build());

        Set<CustomerLicense> set2 = Set.of(CustomerLicense.builder()
                        .contentId(UUID.fromString("079415d6-9acb-40f3-ab2f-03f4db7001e2"))
                        .licenseType(LicenseType.MATCH)
                        .customerId(UUID.fromString("cfa89770-72df-4673-b7d7-abe5aaa3c308"))
                        .build(),
                CustomerLicense.builder()
                        .contentId(UUID.fromString("b8d2c400-7a85-4d9b-bd45-efbc3b7cd4d9"))
                        .licenseType(LicenseType.MATCH)
                        .customerId(UUID.fromString("cfa89770-72df-4673-b7d7-abe5aaa3c308"))
                        .build());

        licensesPerUser = Map.of(UUID.fromString("f411c4e7-1278-4fa3-8ab9-e8c264e12952"), set1,
                UUID.fromString("cfa89770-72df-4673-b7d7-abe5aaa3c308"), set2);
    }

    @Override
    public Set<CustomerLicense> getCustomerLicenses(UUID customerId) {
        return licensesPerUser.get(customerId);
    }
}
