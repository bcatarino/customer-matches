package com.somecompany.customermatches.rest;

import com.somecompany.customermatches.model.Match;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.server.PathParam;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/customer/{customerId}")
public class CustomerController {
    @RequestMapping("/matches")
    @ResponseBody
    public List<Match> getCustomerMatches(@PathParam("customerId") String customerId) {
        return Collections.emptyList();
    }
}
