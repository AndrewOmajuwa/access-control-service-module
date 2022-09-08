package com.devoteam;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info =
@Info(title = "Access-Control-Service API", version = "1.0", description = "Documentation Access-Control-Service API v1.0")
)
public class AccessControlServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(AccessControlServiceApplication.class, args);
	}

}
