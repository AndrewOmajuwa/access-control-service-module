package com.devoteam.accesscontrolservice.controller;

import com.devoteam.CheckPermissionService;
import com.devoteam.accesscontrolservice.domain.*;
import com.devoteam.accesscontrolservice.requests.post.UserPostRequest;
import com.devoteam.accesscontrolservice.response.UserResponse;
import com.devoteam.accesscontrolservice.util.Utility;
import com.devoteam.accesscontrolservice.wrapper.PageableResponse;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CreateUserControllerTest {

    @Autowired
    private TestRestTemplate testRestTemplate;
    @MockBean
    private KeycloakAdminClient keycloakAdminClient;
    @MockBean
    CheckPermissionService checkPermissionServiceMock;

    @BeforeEach

    public void setUp() {
        UserPostRequest userPostRequest = Utility.createUserKeycloakToBeSaved();

        BDDMockito.when(keycloakAdminClient.createUserUuid(userPostRequest.getFirstName(), userPostRequest.getLastName(), userPostRequest.getEmail(), userPostRequest.getPassword())).thenReturn("48553c16-56e4-42e6-8cf4-25cee7609a33");

        BDDMockito.when(checkPermissionServiceMock.validateAccess(Mockito.anyString(),Mockito.anyString(),Mockito.anyString())).thenReturn(true);
    }

    @Test
    @DisplayName("Save creates user when successfull")
    void save_User_WhenSuccessfull() {

        UserResponse userResponse = testRestTemplate.exchange("/api/v1/users", HttpMethod.POST, Utility.createJsonHttpEntity(Utility.createUserKeycloakToBeSaved()), UserResponse.class).getBody();
        System.out.println(testRestTemplate.exchange("/api/v1/users", HttpMethod.POST, Utility.createJsonHttpEntity(Utility.createUserKeycloakToBeSaved()), UserResponse.class));
        Assertions.assertThat(userResponse).isNotNull();
        Assertions.assertThat(userResponse.getUuid()).isNotNull();
    }


    @Test
    @DisplayName("findAll returns a paginated list of users when called successfully")
    void findAll_ReturnsPaginatedListOfUsers_WhenCalledSuccessfully() {

        PageableResponse<UserKeyCloak> usersKeyCloak = testRestTemplate.exchange("/api/v1/users?email={email}&firstName={firstName}&lastName={lastName}", HttpMethod.GET, null, new ParameterizedTypeReference<PageableResponse<UserKeyCloak>>() {
        }, "admin@user", "admin", "user").getBody();

        Assertions.assertThat(usersKeyCloak).isNotNull();

        Assertions.assertThat(usersKeyCloak).isNotEmpty();

        Assertions.assertThat(usersKeyCloak.stream().count()).isEqualTo(1);

    }

    @Test
    @DisplayName("findAll does not return a paginated list of business-function-permissions when called without parameter")
    void findAll_DoesNotReturnAListOfPaginatedBusinessFunctionPermissions_WhenCalledWithoutParameter() {

        PageableResponse<UserKeyCloak> usersKeyCloak = testRestTemplate.exchange("/api/v1/users?email={email}&firstName={firstName}&lastName={lastName}", HttpMethod.GET, null, new ParameterizedTypeReference<PageableResponse<UserKeyCloak>>() {
        }, "", "", "").getBody();

        Assertions.assertThat(usersKeyCloak.getNumberOfElements()).isZero();

        Assertions.assertThat(usersKeyCloak).isEmpty();

    }

}