package com.somecompany.customermatches.rest;

import com.somecompany.customermatches.services.LicensingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/customer/{customerId}")
@RequiredArgsConstructor
@Slf4j
public class CustomerController {
    private final LicensingService licensingService;
    private final MatchResponseTransformer matchResponseTransformer;

    @RequestMapping("/matches")
    public ResponseEntity<Set<MatchDto>> getCustomerMatches(
            @PathVariable("customerId") String customerId,
            @RequestParam(name = "summaryType", defaultValue = "AvB") String summaryTypeStr) {
        return getSummaryType(summaryTypeStr)
                .map(summaryType -> ResponseEntity.ok(licensingService.getLicensedMatches(customerId)
                        .stream()
                        .map(match -> matchResponseTransformer.transform(match, summaryType))
                        .collect(Collectors.toSet())))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build());
    }

    private Optional<SummaryType> getSummaryType(String summaryType) {
        try {
            return Optional.of(SummaryType.valueOf(summaryType));
        } catch (IllegalArgumentException e) {
            log.error("invalid summaryType {} for request", summaryType);
        }
        return Optional.empty();
    }
}
