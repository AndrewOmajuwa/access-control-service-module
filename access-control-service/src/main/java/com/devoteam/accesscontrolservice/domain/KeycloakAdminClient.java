package com.devoteam.accesscontrolservice.domain;

import com.devoteam.accesscontrolservice.exception.BadRequest;
import com.devoteam.accesscontrolservice.repository.UserRepository;
import java.util.Collections;
import java.util.List;
import javax.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.CreatedResponseUtil;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class KeycloakAdminClient {

    @Value("${user.passwordClient}")
    private String clientSecret;

    @Value(("${user.clientId}"))
    private String clientId;

    @Value(("${REALM}"))
    private String realm;

    private final UserRepository userRepository;
    public String createUserUuid(String firstName, String lastName, String email, String password){
        checkIfEmailAlreadyExists(email);
        UsersResource usersResource = getKeycloakClient().realm(realm).users();
        UserRepresentation user = getUserRepresentation(firstName, lastName, email);
        Response response = usersResource.create(user);
        String userId = CreatedResponseUtil.getCreatedId(response);
        UserResource userResource = usersResource.get(userId);
        userResource.resetPassword(getPasswordCredentialRepresentation(password));
        return userId;
    }

    private Keycloak getKeycloakClient() {
      return KeycloakBuilder.builder()
                .serverUrl("http://localhost:8180").realm(realm)
                .grantType(OAuth2Constants.CLIENT_CREDENTIALS)
                .clientId(clientId)
                .clientSecret(clientSecret).build();
    }

    private UserRepresentation getUserRepresentation(String firstName, String lastName, String email) {
        UserRepresentation user = new UserRepresentation();
        user.setEnabled(true);
        user.setUsername(email);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setAttributes(Collections.singletonMap("origin", List.of("demo")));
        return user;
    }

    private CredentialRepresentation getPasswordCredentialRepresentation(String password) {
        CredentialRepresentation passwordCred = new CredentialRepresentation();
        passwordCred.setTemporary(false);
        passwordCred.setType(CredentialRepresentation.PASSWORD);
        passwordCred.setValue(password);
        return passwordCred;
    }

    public void checkIfEmailAlreadyExists(String email){
        boolean present = userRepository.searchByEmail(email).stream().findAny().isPresent();
        if(present){
            throw new BadRequest("There is already an existing user registered with this email address");
        }
    }
}
