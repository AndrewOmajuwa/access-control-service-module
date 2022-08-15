package com.devoteam.accesscontrolservice.controller;

import com.devoteam.CheckPermissionService;
import com.devoteam.accesscontrolservice.domain.*;
import com.devoteam.accesscontrolservice.repository.PermissionRepository;
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
import org.springframework.data.domain.Page;
import org.springframework.http.*;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "/create_admin_user_mysql.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class PermissionControllerTest {

    @Autowired
    private TestRestTemplate testRestTemplate;
    @Autowired
    private PermissionRepository permissionRepository;
    @Autowired
    private Utility utility;
    @Autowired
    @MockBean
    private CheckPermissionService checkPermissionServiceMock;

    @BeforeEach
    public void setUp(){

        BDDMockito.when(checkPermissionServiceMock.validateAccess(Mockito.anyString(),Mockito.anyString(),Mockito.anyString())).thenReturn(true);

    }

    @Test
    @DisplayName("Save creates Permission when successfull")
    void save_Permission_WhenSuccessfull(){

        PermissionResponse permissionResponse = utility.createPermission();
        Assertions.assertThat(permissionResponse).isNotNull();
        Assertions.assertThat(permissionResponse.getId()).isNotNull();
        Assertions.assertThat(permissionResponse.getId()).isEqualTo(1);

    }
    @Test
    @DisplayName("Save does not create Permission when input is blank")
    void save_DoesNotCreatePermission_WhenInputIsBlank(){

        PermissionResponse permissionResponse = testRestTemplate
                .exchange( "/api/v1/permissions", HttpMethod.POST, Utility.createJsonHttpEntity(createPermissionNotToBeSaved()), PermissionResponse.class)
                .getBody();

        Assertions.assertThat(permissionResponse.getId()).isNull();
    }

    @Test
    @DisplayName("Save does not create Permission when already present")
    void doesNotSave_Permission_WhenAlreadyPresent(){

        PermissionResponse permissionResponse1 = utility.createPermission();
        PermissionResponse permissionResponse2 = utility.createPermission();
        Assertions.assertThat(permissionResponse1.getId()).isEqualTo(permissionResponse2.getId());
        Assertions.assertThat(permissionRepository.findById(3)).isEmpty();

    }

    @Test
    @DisplayName("findAll returns a paginated list of permissions when called successfully")
    void findAll_ReturnsListOfPermission_WhenCalledSuccessfully(){

        PageableResponse<Permission> permissions = testRestTemplate.exchange("/api/v1/permissions", HttpMethod.GET, null, new ParameterizedTypeReference<PageableResponse<Permission>>() {
        }).getBody();

        Assertions.assertThat(permissions).isNotNull();

        Assertions.assertThat(permissions).isNotEmpty();

        Assertions.assertThat(permissions.toList().get(0).getId()).isEqualTo(1);

    }

    @Test
    @DisplayName("findById returns a Permission when successfull")
    public void findById_ReturnsPermission_WhenSuccessfull(){

        ResponseEntity<Permission> permission = testRestTemplate.exchange("/api/v1/permissions/1", HttpMethod.GET, null, new ParameterizedTypeReference<>() {
        });

        Assertions.assertThat(permission).isNotNull();

        Assertions.assertThat(permission.getBody().getId()).isNotNull();

        Assertions.assertThat(permission.getBody().getId()).isEqualTo(1);

    }

    @Test
    @DisplayName("findById returns 404 Not Found when id doesnt exist")
    void findById_Returns404NotFound_WhenIdDoesntExist(){

        ResponseEntity<Permission> permission = testRestTemplate.exchange("/api/v1/permissions/3", HttpMethod.GET, null, new ParameterizedTypeReference<>() {
        });

        Assertions.assertThat(permission.getBody().getId()).isNull();

        Assertions.assertThat(permission.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);

    }


    public PermissionPostRequest createPermissionNotToBeSaved(){
        return PermissionPostRequest.builder()
                .name("")
                .build();
    }

}