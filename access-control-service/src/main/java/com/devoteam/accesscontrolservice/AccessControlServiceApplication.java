package com.devoteam.accesscontrolservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.devoteam.library.service", "com.devoteam.accesscontrolservice"} )
public class AccessControlServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(AccessControlServiceApplication.class, args);
	}

}
