package com.somecompany.customermatches;

import com.somecompany.customermatches.rest.CustomerController;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CustomerMatchesApplicationTests {
	@Autowired
	private CustomerController customerMatchesController;

	@Test
	void contextLoads() {
		Assertions.assertThat(customerMatchesController).isNotNull();
	}

}
