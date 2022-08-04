package com.devoteam;

import lombok.RequiredArgsConstructor;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Configuration
@Component
@RequiredArgsConstructor
public class CheckPermissionService {

    private final RestTemplate restTemplate;


    public boolean validateAccess(String applicationName, String functionName, String permission){

        HttpEntity<String> httpEntity = new HttpEntity<>(createJsonHeader());

        HttpStatus httpStatus = restTemplate.exchange("http://access-control-service/api/v1/validate-access?applicationName={applicationName}&functionName={functionName}&permission={permission}", HttpMethod.GET, httpEntity, HttpStatus.class, applicationName, functionName, permission).getBody();

        return httpStatus == HttpStatus.OK;

    }

    public static HttpHeaders createJsonHeader(){

        String token = getToken();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setBearerAuth(token);

        return httpHeaders;
    }

    private static String getToken() {
        KeycloakAuthenticationToken keycloakAuthenticationToken = (KeycloakAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        var token = keycloakAuthenticationToken.getAccount().getKeycloakSecurityContext().getTokenString();

        return token == null ? "Token not found please authenticate" : token;

    }
}
