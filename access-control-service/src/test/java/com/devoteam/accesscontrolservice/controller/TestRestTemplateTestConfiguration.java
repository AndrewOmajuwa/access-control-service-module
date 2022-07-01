package com.devoteam.accesscontrolservice.controller;

import com.google.common.net.HttpHeaders;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class TestRestTemplateTestConfiguration {
    @Value("${user.token}")
    private String token;

    @Bean
    public RestTemplateBuilder restTemplateBuilder() {
        return new RestTemplateBuilder().additionalInterceptors((httpRequest, bytes, clientHttpRequestExecution) -> {
            httpRequest.getHeaders().add(HttpHeaders.AUTHORIZATION,
                    "Bearer " + token);
            return clientHttpRequestExecution.execute(httpRequest, bytes);
        });
    }
}
