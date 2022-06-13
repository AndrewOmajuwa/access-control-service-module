package com.devoteam.accesscontrolservice.domain;

import com.devoteam.accesscontrolservice.exception.BadRequest;
import com.devoteam.accesscontrolservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.CreatedResponseUtil;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import javax.ws.rs.core.Response;
import java.util.Arrays;
import java.util.Collections;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class KeycloakAdminClient {

    @Value("${user.password}")
    private String password;

    private final UserRepository userRepository;
    public UUID createUserUuid(String firstName, String lastName, String email){


        String serverUrl = "http://localhost:8180/auth";
        String realm = "devoteam";
        String clientId = "idm-client";
        String clientSecret = password;

        Keycloak keycloak = KeycloakBuilder.builder()
                .serverUrl(serverUrl).realm(realm)
                .grantType(OAuth2Constants.CLIENT_CREDENTIALS)
                .clientId(clientId)
                .clientSecret(clientSecret).build();

        UserRepresentation user = new UserRepresentation();
        user.setEnabled(true);
        user.setUsername(email);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setAttributes(Collections.singletonMap("origin", Arrays.asList("demo")));

        checkIfEmailAlreadyExists(email);

        RealmResource realmResource = keycloak.realm(realm);
        UsersResource usersResource = realmResource.users();

        // Create user (requires manage-users role)
        Response response = usersResource.create(user);
        String userId = CreatedResponseUtil.getCreatedId(response);

        return UUID.fromString(userId);
    }

    public void checkIfEmailAlreadyExists(String email){
        boolean present = userRepository.searchByEmail(email).stream().findAny().isPresent();
        if(present){
            throw new BadRequest("There is already an existing user registered with this email address");
        }
    }
}
