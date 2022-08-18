package com.devoteam.accesscontrolservice.controller;

import com.devoteam.CheckPermissionService;
import com.devoteam.accesscontrolservice.domain.*;
import com.devoteam.accesscontrolservice.post_request.UserPostRequest;
import com.devoteam.accesscontrolservice.put_request.BusinessFunctionPutRequest;
import com.devoteam.accesscontrolservice.repository.BusinessFunctionRepository;
import com.devoteam.accesscontrolservice.response.BusinessFunctionResponse;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "/create_admin_user_mysql.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class BusinessFunctionControllerTest {

    @Autowired
    private BusinessFunctionRepository businessFunctionRepository;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private Utility utility;

    @MockBean
    private CheckPermissionService checkPermissionServiceMock;

    @MockBean
    private KeycloakAdminClient keycloakAdminClient;

    @BeforeEach
    public void setUp(){

        UserPostRequest userPostRequest = Utility.createUserKeycloakToBeSaved();

        BDDMockito.when(keycloakAdminClient.createUserUuid(userPostRequest.getFirstName(), userPostRequest.getLastName(), userPostRequest.getEmail(), userPostRequest.getPassword())).thenReturn("48553c16-56e4-42e6-8cf4-25cee7609a33");

        BDDMockito.when(checkPermissionServiceMock.validateAccess(Mockito.anyString(),Mockito.anyString(),Mockito.anyString())).thenReturn(true);

    }

    @Test
    @DisplayName("Save creates Business Function when successfull")
    void save_BusinessFunction_WhenSuccessfull(){

        BusinessFunctionResponse businessFunctionResponse = utility.createBusinessFunction();

        Assertions.assertThat(businessFunctionResponse).isNotNull();

        Assertions.assertThat(businessFunctionResponse.getId()).isNotNull();

        Assertions.assertThat(businessFunctionResponse.getId()).isEqualTo(8);

    }

    @Test
    @DisplayName("Save does not create Business Function when already present")
    void doesNotSave_BusinessFunction_WhenAlreadyPresent(){

        BusinessFunctionResponse businessFunction1 = utility.createBusinessFunction();

        BusinessFunctionResponse businessFunction2 = utility.createBusinessFunction();

        Assertions.assertThat(businessFunction1.getId()).isEqualTo(businessFunction2.getId());

        Assertions.assertThat(businessFunctionRepository.findById(9)).isEmpty();

    }

    @Test
    @DisplayName("findAll returns a paginated list of business-functions when called successfully")
    void findAll_ReturnsListOfBusinessFunctions_WhenCalledSuccessfully(){

        PageableResponse<BusinessFunction> businessFunctions = testRestTemplate.exchange("/api/v1/business-functions", HttpMethod.GET, null, new ParameterizedTypeReference<PageableResponse<BusinessFunction>>() {
        }).getBody();

        Assertions.assertThat(businessFunctions).isNotNull();

        Assertions.assertThat(businessFunctions).isNotEmpty();

        Assertions.assertThat(businessFunctions.toList().get(0).getId()).isEqualTo(2);

    }

    @Test
    @DisplayName("findById returns a business-function when successfull")
    void findById_ReturnsBusinessFunction_WhenSuccessfull(){

        ResponseEntity<BusinessFunction> businessFunction = testRestTemplate.exchange("/api/v1/business-functions/1", HttpMethod.GET, null, new ParameterizedTypeReference<>() {
        });

        Assertions.assertThat(businessFunction).isNotNull();

        Assertions.assertThat(businessFunction.getBody().getId()).isNotNull();

        Assertions.assertThat(businessFunction.getBody().getId()).isEqualTo(1);

    }

    @Test
    @DisplayName("findById returns 404 Not Found when id doesnt exist")
    void findById_Returns404NotFound_WhenIdDoesntExist(){

        ResponseEntity<BusinessFunction> businessFunction = testRestTemplate.exchange("/api/v1/business-functions/8", HttpMethod.GET, null, new ParameterizedTypeReference<>() {
        });

        Assertions.assertThat(businessFunction.getBody().getId()).isNull();

        Assertions.assertThat(businessFunction.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);

    }

    @Test
    @DisplayName("updated business function replaces existing business function when successfully executed")
    void updatedBusinessFunction_ReplacesExistingBusinessFunction_WhenSuccessfullyExecuted(){

        BusinessFunctionPutRequest updatedBusinessFunction = BusinessFunctionPutRequest.builder().id(1).applicationName("Updated-Name").functionName("Updated-Name").build();

        ResponseEntity<Void> responseEntity = testRestTemplate.exchange("/api/v1/business-functions", HttpMethod.PUT, Utility.createJsonHttpEntity(updatedBusinessFunction), Void.class);

        Assertions.assertThat(responseEntity).isNotNull();

        Assertions.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

        Assertions.assertThat(businessFunctionRepository.findById(1).get().getApplicationName()).isEqualTo("Updated-Name");

        Assertions.assertThat(businessFunctionRepository.findById(1).get().getFunctionName()).isEqualTo("Updated-Name");

    }

    @Test
    @DisplayName("update business function returns 400 BadRequest when business function application or function name is null or blank")
    void updatedBusinessFunction_Returns400BadRequest_WhenBusinessFunctionApplicationNameIsNullOrBlank(){

        BusinessFunctionPutRequest updatedBusinessFunction1 = BusinessFunctionPutRequest.builder().id(1).applicationName(null).functionName("Updated-Name").build();

        ResponseEntity<Void> responseEntity1 = testRestTemplate.exchange("/api/v1/business-functions", HttpMethod.PUT, Utility.createJsonHttpEntity(updatedBusinessFunction1), Void.class);

        BusinessFunctionPutRequest updatedBusinessFunction2 = BusinessFunctionPutRequest.builder().id(1).applicationName("Updated-Name").functionName(null).build();

        ResponseEntity<Void> responseEntity2 = testRestTemplate.exchange("/api/v1/business-functions", HttpMethod.PUT, Utility.createJsonHttpEntity(updatedBusinessFunction2), Void.class);

        Assertions.assertThat(responseEntity1.getBody()).isNull();

        Assertions.assertThat(responseEntity1.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);

        Assertions.assertThat(responseEntity2.getBody()).isNull();

        Assertions.assertThat(responseEntity2.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);

    }

    @Test
    @DisplayName("update business function returns 404 ResourceNotfound when business function id does not exist")
    void updatedBusinessFunction_Returns400BadRequest_WhenBusinessFunctionIdDoesNotExist(){

        BusinessFunctionPutRequest updatedBusinessFunction = BusinessFunctionPutRequest.builder().id(100).applicationName("Updated-Name").functionName("Updated-Name").build();

        ResponseEntity<Void> responseEntity = testRestTemplate.exchange("/api/v1/business-functions", HttpMethod.PUT, Utility.createJsonHttpEntity(updatedBusinessFunction), Void.class);

        Assertions.assertThat(responseEntity.getBody()).isNull();

        Assertions.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);

    }

    @Test
    @DisplayName("business-function does not get updated when business-function name is not unique")
    void BusinessFunction_DoesNotGetUpdated_WhenBusinessFunctionNameIsNotUnique(){

        BusinessFunctionPutRequest updatedBusinessFunction1 = BusinessFunctionPutRequest.builder().id(1).applicationName("Updated-Name").functionName("Updated-Name").build();

        BusinessFunctionPutRequest updatedBusinessFunction2 = BusinessFunctionPutRequest.builder().id(2).applicationName("Updated-Name").functionName("Updated-Name").build();

        BusinessFunctionPutRequest updatedBusinessFunction3 = BusinessFunctionPutRequest.builder().id(2).applicationName("Different").functionName("Updated-Name").build();

        BusinessFunctionPutRequest updatedBusinessFunction4 = BusinessFunctionPutRequest.builder().id(2).applicationName("Updated-Name").functionName("Different").build();

        ResponseEntity<Void> responseEntity1 = testRestTemplate.exchange("/api/v1/business-functions", HttpMethod.PUT, Utility.createJsonHttpEntity(updatedBusinessFunction1), Void.class);

        ResponseEntity<Void> responseEntity2 = testRestTemplate.exchange("/api/v1/business-functions", HttpMethod.PUT, Utility.createJsonHttpEntity(updatedBusinessFunction2), Void.class);

        ResponseEntity<Void> responseEntity3 = testRestTemplate.exchange("/api/v1/business-functions", HttpMethod.PUT, Utility.createJsonHttpEntity(updatedBusinessFunction3), Void.class);

        ResponseEntity<Void> responseEntity4 = testRestTemplate.exchange("/api/v1/business-functions", HttpMethod.PUT, Utility.createJsonHttpEntity(updatedBusinessFunction4), Void.class);

        Assertions.assertThat(responseEntity1.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

        Assertions.assertThat(responseEntity2.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);

        Assertions.assertThat(responseEntity3.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

        Assertions.assertThat(responseEntity4.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

    }


    @Test
    @DisplayName("delete removes a business function when successfully executed")
    void delete_RemovesABusinessFunction_WhenSuccessfullyExecuted(){

        ResponseEntity<Void> responseEntity = testRestTemplate.exchange("/api/v1/business-functions/7", HttpMethod.DELETE, null, Void.class);

        Assertions.assertThat(responseEntity).isNotNull();

        Assertions.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

        Assertions.assertThat(businessFunctionRepository.findById(7).isEmpty());

    }


    @Test
    @DisplayName("delete business function returns 400 BadRequest when business function is associated with permission")
    void updatedBusinessFunction_Returns400BadRequest_WhenBusinessFunctionIsAssociatedWithPermission(){

        ResponseEntity<Void> responseEntity = testRestTemplate.exchange("/api/v1/business-functions/1", HttpMethod.DELETE, null, Void.class);

        Assertions.assertThat(responseEntity.getBody()).isNull();

        Assertions.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);

    }

    @Test
    @DisplayName("delete business function returns 404 ResourceNotfound when business function id does not exist")
    void deleteBusinessFunction_Returns404ResourceNotFound_WhenBusinessFunctionIdDoesNotExist(){


        ResponseEntity<Void> responseEntity = testRestTemplate.exchange("/api/v1/business-functions/100", HttpMethod.DELETE, null, Void.class);

        Assertions.assertThat(responseEntity.getBody()).isNull();

        Assertions.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);

    }
}