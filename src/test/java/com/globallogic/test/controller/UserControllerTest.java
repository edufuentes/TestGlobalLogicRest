package com.globallogic.test.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.globallogic.test.service.UserService;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

/**
 * User Controller Tests.
 */
@ExtendWith(SpringExtension.class)
@WebMvcTest(value = UserController.class)
public class UserControllerTest {

  /**
   * Mock Mvc.
   */
  @Autowired
  private MockMvc mockMvc;

  /**
   * Rest Template for testing the api.
   */
  @MockBean
  private UserService planetService;

  /**
   * List all Users test.
   * @throws Exception exception
   */
 /* @Test
  @DisplayName("List all Users test")
  public void listAllUsersTest() throws Exception {
    User planet1 = new User();
    planet1.setName("Mercury");
    when(planetService.getAllUsers()).thenReturn(List.of(planet1));
    MockHttpServletResponse response = mockMvc.perform(get("/planet")
        .accept(MediaType.APPLICATION_JSON))
        .andReturn().getResponse();


    assertThat(response.getStatus(), equalTo(HttpStatus.OK.value()));
    assertThat(response.getContentAsString(), equalTo(getJsonFromObject(List.of(planet1))));
    verify(planetService,times(1)).getAllUsers();
  }
*/
  /**
   * To Convert an Object to JSON String.
   * @param o Object
   * @return Object as String
   * @throws JsonProcessingException throws JsonProcessingException
   *//*
  private static  String getJsonFromObject(Object o) throws JsonProcessingException {
    ObjectMapper mapper = new ObjectMapper();
    mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    return mapper.writeValueAsString(o);
  }*/
}
