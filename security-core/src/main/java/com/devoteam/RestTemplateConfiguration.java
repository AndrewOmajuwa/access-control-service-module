package com.devoteam;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class RestTemplateConfiguration {
    @Value("${TOKEN}")
    private String token;

    @Bean
    @LoadBalanced
    public RestTemplateBuilder restTemplateBuilder() {
        return new RestTemplateBuilder().additionalInterceptors((httpRequest, bytes, clientHttpRequestExecution) -> {
            httpRequest.getHeaders().add(HttpHeaders.AUTHORIZATION,
                    "Bearer " + token);
            return clientHttpRequestExecution.execute(httpRequest, bytes);
        });
    }
}
