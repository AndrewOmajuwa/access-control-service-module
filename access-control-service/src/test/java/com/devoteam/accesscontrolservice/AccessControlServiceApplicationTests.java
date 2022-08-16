package com.devoteam.accesscontrolservice;

import com.devoteam.accesscontrolservice.domain.KeycloakAdminClient;
import com.devoteam.accesscontrolservice.domain.UserPostRequest;
import com.devoteam.accesscontrolservice.util.Utility;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
class AccessControlServiceApplicationTests {

	@MockBean
	private KeycloakAdminClient keycloakAdminClient;

	@Test
	void contextLoads() {
	}

}
