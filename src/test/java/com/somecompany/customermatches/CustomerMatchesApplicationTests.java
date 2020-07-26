package com.somecompany.customermatches;

import com.somecompany.customermatches.rest.CustomerController;
import com.somecompany.customermatches.rest.MatchResponseTransformer;
import com.somecompany.customermatches.services.LicensingService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CustomerMatchesApplicationTests {
	@Autowired
	private CustomerController customerMatchesController;

	@Autowired
	private LicensingService licensingService;

	@Autowired
	private MatchResponseTransformer matchResponseTransformer;

	@Test
	void contextLoads() {
		Assertions.assertThat(matchResponseTransformer).isNotNull();
		Assertions.assertThat(licensingService).isNotNull();
		Assertions.assertThat(customerMatchesController).isNotNull();
	}
}
