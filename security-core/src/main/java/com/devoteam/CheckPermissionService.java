package com.devoteam;

import lombok.RequiredArgsConstructor;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

@Configuration
@Component
@RequiredArgsConstructor
public class CheckPermissionService {

    private final RestTemplate restTemplate;

    public boolean validateAccess(String applicationName, String functionName, String permission){

        HttpEntity<String> httpEntity = new HttpEntity<>(createJsonHeader());

        HttpStatus httpStatus = restTemplate.exchange("http://access-control-service/api/v1/validate-access?applicationName={applicationName}&functionName={functionName}&permission={permission}", HttpMethod.GET, httpEntity, HttpStatus.class, applicationName, functionName, permission).getStatusCode();

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

        if(token == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Token not found");
        }

        return token;

    }
}
