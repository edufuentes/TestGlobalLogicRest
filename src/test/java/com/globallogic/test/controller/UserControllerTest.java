package com.globallogic.test.controller;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.globallogic.test.entity.Phone;
import com.globallogic.test.entity.User;
import com.globallogic.test.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.validation.MapBindingResult;

import java.util.*;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

/**
 * User Controller Tests.
 */
/* @RunWith (SpringRunner.class)
@SpringBootTest
@WebAppConfiguration */
@ExtendWith(SpringExtension.class)
@WebMvcTest(value = UserController.class, excludeFilters = {@ComponentScan.Filter(
        type = FilterType.REGEX, pattern = "com.globallogic.test.auth.*")})
public class UserControllerTest {


  private UserController controller;

  private ObjectMapper mapper = new ObjectMapper();

  /**
   * Mock Mvc.
   */
  @Autowired
  private MockMvc mockMvc;

  /**
   * Rest Template for testing the api.
   */
  @MockBean
  private UserService userService;



    @Test
    @DisplayName("should Not Allow Access test")
    public void shouldNotAllowAccessToUnauthenticatedUsers() throws Exception {
        MockHttpServletResponse response = mockMvc.perform(get("/api/Users/getUsers")
                .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();
        assertThat(response.getStatus(), equalTo(HttpStatus.UNAUTHORIZED.value()));
    }


  /**
   * List all Users test.
   * @throws Exception exception
   */
  @Test
  @DisplayName("List all Users test")
  @WithMockUser(username = "eduardo", password = "12345")
  public void listAllUsersTest() throws Exception {

    //String token = TokenAuthenticationService.createToken("eduardo");

    User user = new User();
    user.setEmail("aaA2a2aaa23@hotmail.com");

    List<User> userList =  new ArrayList<User>();
    userList.add(user);
    //when(planetService.getAllUsers()).thenReturn(List.of(user));

    userService = mock(UserService.class);
    controller = new UserController(userService);


    when(userService.getAllUsers()).thenReturn(userList);

    ///api/Users/getUsers
    ResponseEntity<List<User>> response = controller.index();

    /*MockHttpServletResponse response = mockMvc.perform(get("/api/Users/getUsers")
//            .header("Authorization", "Bearer " + token)
        .accept(MediaType.APPLICATION_JSON))
        .andReturn().getResponse();*/

    List<User> userListaResp = (List<User>) response.getBody();

    assertThat(response.getStatusCode().value(), equalTo(HttpStatus.OK.value()));
    assertThat(getJsonFromObject(response.getBody()),equalTo(getJsonFromObject(userList)));
    assertThat(response.getBody().get(0).getEmail(), equalTo(userList.get(0).getEmail()));
    verify(userService,times(1)).getAllUsers();
  }




  /**
   * To Convert an Object to JSON String.
   * @param o Object
   * @return Object as String
   * @throws JsonProcessingException throws JsonProcessingException
   */
  private static  String getJsonFromObject(Object o) throws JsonProcessingException {
    ObjectMapper mapper = new ObjectMapper();
    mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    return mapper.writeValueAsString(o);
  }

  /**
   * Create User test.
   * @throws Exception exception
   */
  @Test
  @DisplayName("Create a new User")
  public void createUserTest() throws Exception {

    User user = new User();
    user.setEmail("aaA2a2aaa2323@hotmail.com");
    user.setIsActive(true);
    user.setCreated(Calendar.getInstance());
    user.setModified(Calendar.getInstance());
    user.setLast_login(Calendar.getInstance());
    user.setName("Pedro Perez");
    user.setPassword("12345678");

    List<Phone> phoneList = new ArrayList<Phone>();
    Phone phone = new Phone();
    phone.setUser(user);
    phone.setCityCode("33");
    phone.setCountryCode("Chile");
    phone.setNumber("+56953074123");

    phoneList.add(phone);
    user.setPhone(phoneList);

    userService = mock(UserService.class);
    controller = new UserController(userService);
    when(userService.saveUser(any(User.class))).thenReturn(user);


    ResponseEntity<Map<String, Object>> response =
            (ResponseEntity<Map<String, Object>>) controller.create(user,
                    new MapBindingResult(new HashMap<>(),"user"),"Token");

    assertThat(response.getStatusCode().value(), equalTo(HttpStatus.CREATED.value()));
   // assertThat(getJsonFromObject(response.getBody()),equalTo(getJsonFromObject(userList)));
    assertThat(response.getBody().get("isactive"), equalTo(user.getIsActive()));
    verify(userService,times(1)).saveUser(user);
  }


  /**
   * Update User test.
   * @throws Exception exception
   */
  @Test
  @DisplayName("Update a new User")
  public void updateUserTest() throws Exception {

    User user = new User();
    user.setId(1L);
    user.setEmail("aaA2a2aaa2323@hotmail.com");
    user.setIsActive(true);
    user.setCreated(Calendar.getInstance());
    user.setModified(Calendar.getInstance());
    user.setLast_login(Calendar.getInstance());
    user.setName("Pedro Perez");
    user.setPassword("12345678");

    List<Phone> phoneList = new ArrayList<Phone>();
    Phone phone = new Phone();
    phone.setUser(user);
    phone.setCityCode("33");
    phone.setCountryCode("Chile");
    phone.setNumber("+56953074123");

    phoneList.add(phone);
    user.setPhone(phoneList);

    //update
    User userUpdate = new User();
    userUpdate.setId(1L);
    userUpdate.setEmail("aaA2a2aaa3333@gmail.com");//cambio de email
    userUpdate.setIsActive(false);//deshabilitado
    userUpdate.setCreated(user.getCreated());
    userUpdate.setModified(user.getModified());
    userUpdate.setLast_login(user.getLast_login());
    userUpdate.setName("Pedro Perez");
    userUpdate.setPassword("12345678");
    userUpdate.setName("Juan");


    userService = mock(UserService.class);
    controller = new UserController(userService);
    when(userService.getUserById(userUpdate.getId())).thenReturn(user);
    when(userService.updateUserById(userUpdate.getId(),userUpdate)).thenReturn(userUpdate);


    ResponseEntity<Map<String, Object>> response =
            (ResponseEntity<Map<String, Object>>) controller.updateUserById(userUpdate,
            new MapBindingResult(new HashMap<>(),"user"),userUpdate.getId());

    assertThat(response.getStatusCode().value(), equalTo(HttpStatus.OK.value()));
    assertThat(((User) response.getBody()).getEmail(),equalTo(userUpdate.getEmail()));
    assertThat(((User) response.getBody()).getIsActive(),equalTo(userUpdate.getIsActive()));
    assertThat(((User) response.getBody()).getName(),equalTo(userUpdate.getName()));
    //assertThat(response.getBody().get("isactive"), equalTo(user.getIsActive()));
    verify(userService,times(1)).updateUserById(userUpdate.getId(),userUpdate);
  }



  /**
   * Delete User test.
   * @throws Exception exception
   */
  @Test
  @DisplayName("Delete User")
  public void deleteUserTest() throws Exception {

    User user = new User();
    user.setId(1L);

    userService = mock(UserService.class);
    controller = new UserController(userService);
    when(userService.getUserById(user.getId())).thenReturn(user);
    Mockito.doNothing().when(userService).deleteUserById(user.getId());


    ResponseEntity<Map<String, Object>> response =
            (ResponseEntity<Map<String, Object>>) controller.deleteUserById(user.getId());

    assertThat(response.getStatusCode().value(), equalTo(HttpStatus.OK.value()));
    assertThat(((Map<String, Object>)response.getBody()).get("mensaje"), equalTo("El usuario eliminado con éxito!"));
    verify(userService,times(1)).deleteUserById(user.getId());
  }


  /**
   * Delete User test.
   * @throws Exception exception
   */
  @Test
  @DisplayName("Delete User when is not found")
  public void deleteUserWhenIsNotFoundTest() throws Exception {

    User user = new User();
    user.setId(1L);

    userService = mock(UserService.class);
    controller = new UserController(userService);
//    when(userService.getUserById(user.getId())).thenReturn(null);

    Mockito.doThrow(new DataAccessException("User Not Found"){ }).when(userService).deleteUserById(user.getId());


    ResponseEntity<Map<String, Object>> response =
            (ResponseEntity<Map<String, Object>>) controller.deleteUserById(user.getId());

    assertThat(response.getStatusCode().value(), equalTo(HttpStatus.INTERNAL_SERVER_ERROR.value()));
    assertThat(((Map<String, Object>)response.getBody()).get("mensaje"), equalTo("Error al eliminar el usuario de la base de datos"));
    verify(userService,times(1)).deleteUserById(user.getId());



  }



}
