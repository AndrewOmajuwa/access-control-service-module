package com.devoteam.accesscontrolservice.domain;

import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.CreatedResponseUtil;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.UserRepresentation;

import javax.ws.rs.core.Response;
import java.util.Arrays;
import java.util.Collections;


public class KeycloakAdminClient {

    public User createUser(String email, String firstName, String lastName){

        String serverUrl = "http://localhost:8180/auth";
        String realm = "devoteam";
        String clientId = "idm-client";
        String clientSecret = "6YqiYm7Kz0Is1h2GkiUaRGzmk6x1QIcI";

        Keycloak keycloak = KeycloakBuilder.builder()
                .serverUrl(serverUrl).realm(realm)
                .grantType(OAuth2Constants.CLIENT_CREDENTIALS)
                .clientId(clientId)
                .clientSecret(clientSecret).build();

        UserRepresentation user = new UserRepresentation();
        user.setEnabled(true);
        user.setUsername(firstName + "_" + lastName);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setAttributes(Collections.singletonMap("origin", Arrays.asList("demo")));

        RealmResource realmResource = keycloak.realm(realm);
        UsersResource usersResource = realmResource.users();
        System.out.println("User Resource " + usersResource);

        // Create user (requires manage-users role)
        Response response = usersResource.create(user);
        System.out.printf("Response: %s %s%n", response.getStatus(), response.getStatusInfo());
        System.out.println(response.getLocation());
        String userId = CreatedResponseUtil.getCreatedId(response);
        System.out.printf("User created with userId: %s%n", userId);

        return User.builder()
                .firstName(firstName)
                .lastName(lastName)
                .email(email)
                .uuid(userId)
                .build();
    }
}
