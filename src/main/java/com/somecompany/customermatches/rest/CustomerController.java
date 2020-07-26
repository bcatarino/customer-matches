package com.somecompany.customermatches.rest;

import com.somecompany.customermatches.model.Match;
import com.somecompany.customermatches.services.LicensingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/customer/{customerId}")
@RequiredArgsConstructor
public class CustomerController {
    private final LicensingService licensingService;

    @RequestMapping("/matches")
    @ResponseBody
    public List<Match> getCustomerMatches(@PathVariable("customerId") String customerId) {
        return licensingService.getLicensedMatches(customerId);
    }
}
