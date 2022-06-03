package com.devoteam.accesscontrolservice.domain;

import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.UserRepresentation;

import java.util.Arrays;
import java.util.Collections;


public class KeycloakAdminClient {
    public static void main(String[] args) {

        String serverUrl = "http://localhost:8180/auth";
        String realm = "master";
        String clientId = "idm-client";
        String clientSecret = "7Fls8VV1sgtH57Oqh2xoTIsF22xT3mcq";

        Keycloak keycloak = KeycloakBuilder.builder()
                .serverUrl(serverUrl)
                .realm(realm)
                .username("idm-admin")
                .password("admin")
                .clientId(clientId)
                .grantType(OAuth2Constants.PASSWORD)
                .clientSecret(clientSecret)
                .build();
        UserRepresentation user = new UserRepresentation();
        user.setEnabled(true);
        user.setUsername("tester1");
        user.setFirstName("First");
        user.setLastName("Last");
        user.setEmail("tom+tester1@tdlabs.local");
        user.setAttributes(Collections.singletonMap("origin", Arrays.asList("demo")));

        RealmResource realmResource = keycloak.realm(realm);
        UsersResource usersRessource = realmResource.users();

    }
}
