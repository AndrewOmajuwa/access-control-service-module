package com.devoteam.accesscontrolservice.controller;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;

@TestConfiguration
@Lazy
public class TestRestTemplateTestConfiguration {

    @LocalServerPort
    private int port;

    @Bean
    public TestRestTemplate testRestTemplate() {
        var restTemplate = new RestTemplateBuilder().rootUri("http://localhost:" + port).basicAuthentication("andrew", "devoteam");
        return new TestRestTemplate(restTemplate);
    }

}
