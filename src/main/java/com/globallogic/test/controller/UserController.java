package com.globallogic.test.controller;

import com.globallogic.test.entity.Phone;
import com.globallogic.test.entity.User;
import com.globallogic.test.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;

/**
 * User Rest Controller.
 */
@RestController
@RequestMapping("/api/Users")
@CrossOrigin(origins = "*")
public class UserController {


  Logger LOG = LoggerFactory.getLogger(UserController.class);

  /**
   * Autowiring the User Service.
   */
  @Autowired
  private UserService userService;

  /**
   * get Api to return list of all users.
   * @return List of Users
   */
  @GetMapping("/getUsers")
  public ResponseEntity<List<User>> index() {
    List<User> userList = userService.getAllUsers();
    return new ResponseEntity<>(userList, HttpStatus.OK);
  }

  /**
   * get Api to return the User by Id.
   * @param id Id
   * @return Users
   */
  @GetMapping("/getUser/{id}")
  public ResponseEntity<?> show(@PathVariable("id")  Long id) {

    LOG.info("Init Show - Request: id => " + id);

    Map<String, Object> response = new HashMap<>();
    User userToday = null;

    try {

      userToday  = userService.getUserById(id);

      LOG.info("Usuario encontrado, userToday: " + userToday.toString());

    }catch(NoSuchElementException e) {

      LOG.error("Error: " + e.getMessage(),e);
      response.put("mensaje", "Error: no se pudo editar, el usuario ID: "
              .concat(id.toString().concat(" no existe en la base de datos!")));

      return new ResponseEntity<>(response, HttpStatus.OK);
    }

    LOG.info("Finish Show");

    return new ResponseEntity<User>(userToday, HttpStatus.OK);

  }

  /**
   * Api to save new User.
   * @param user User to add
   * @return Saved User
   */
  @PostMapping(path = "/create",consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {MediaType.APPLICATION_JSON_VALUE })
  public ResponseEntity<?> create(
          @Valid @RequestBody  User user, BindingResult result,@RequestHeader (name="Authorization") String token) {

    LOG.info("Init create - Request user: " + user.toString());

    Map<String, Object> response = new HashMap<>();
    ResponseEntity<?> userResponseEntity = null;

    if(result.hasErrors()) {

      List<String> errors = result.getFieldErrors()
              .stream()
              .map(err -> "El campo '" + err.getField() +"' "+ err.getDefaultMessage())
              .collect(Collectors.toList());

      response.put("mensaje", errors);
      return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
    }

    try {
      user.setCreated(Calendar.getInstance());
      user.setModified(Calendar.getInstance());
      user.setLast_login(Calendar.getInstance());
      User savedUser = userService.saveUser(user);

      response.put("id",savedUser.getId());
      response.put("created",savedUser.getCreated());
      response.put("modified",savedUser.getModified());
      response.put("last_login",savedUser.getLast_login());
      response.put("token",token);
      response.put("isactive",savedUser.getIsActive());

      userResponseEntity = new ResponseEntity<>(response, HttpStatus.CREATED);

      LOG.info("Usuario Creado, user: " + savedUser.toString());

    } catch(DataAccessException e) {

      LOG.error("Error: " + e.getMessage(),e);

      //https://docs.oracle.com/javadb/10.8.3.0/ref/rrefexcept71493.html
      if(e.getMessage().contains("ConstraintViolationException") &&  e.getMessage().contains("23505")){
        response.put("mensaje", "El correo ya registrado.");
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
      }
      response.put("mensaje", "Error al realizar el insert en la base de datos: " + e.getMessage());
      //response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
      return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    LOG.info("Finish create");


    return userResponseEntity;
  }

  /**
   * Api to update an existing User.
   * @param id Id to update
   * @param user User to update
   * @return Updated User
   */
  @PutMapping("update/{id}")
  public ResponseEntity<?> updateUserById(@Valid @RequestBody User user,
                                          BindingResult result,
                                          @PathVariable("id") final Long id) {

    LOG.info("Init updateUserById - Request user: " + user.toString());

    User userUpdated = null;
    Map<String, Object> response = new HashMap<>();
    User userToday = null;
    try {
      userToday  = userService.getUserById(id);

    }catch(NoSuchElementException e){
      response.put("mensaje", "Error: no se pudo editar, el usuario ID: "
              .concat(id.toString().concat(" no existe en la base de datos!")));
      return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
    }

    if(result.hasErrors()) {

      List<String> errors = result.getFieldErrors()
              .stream()
              .map(err -> "El campo '" + err.getField() +"' "+ err.getDefaultMessage())
              .collect(Collectors.toList());

      response.put("mensaje", errors);
      return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
    }

    try {
      user.setCreated(userToday.getCreated());
      user.setModified(Calendar.getInstance());
      user.setLast_login(Calendar.getInstance());

      userUpdated = userService.saveUser(user);

      LOG.info("Usuario Actualizado, user: " + userUpdated.toString());

    } catch (DataAccessException e) {
      LOG.error("Error: " + e.getMessage(),e);
      response.put("mensaje", "Error al actualizar el usuario en la base de datos");
      response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
      return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    response.put("mensaje", "El usuario ha sido actualizado con éxito!");

    LOG.info("Finish updateUserById");

    return new ResponseEntity<>(userUpdated, HttpStatus.OK);
  }

  /**
   * Api to delete a user.
   * @param id Id to delete
   * @return Success Message
   */
  @DeleteMapping("delete/{id}")
  public ResponseEntity<?> deleteUserById(
          @PathVariable("id") final Long id) {

    LOG.info("Init deleteUserById - Request user: " + id);

    Map<String, Object> response = new HashMap<>();
    try {

      userService.deleteUserById(id);
      LOG.info("Usuario Eliminado, user ID: " + id);

    } catch (DataAccessException e) {
      LOG.error("Error: " + e.getMessage(),e);
      response.put("mensaje", "Error al eliminar el usuario de la base de datos");
      //  response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
      return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    response.put("mensaje", "El usuario eliminado con éxito!");

    LOG.info("Finish deleteUserById");

    return new ResponseEntity<>(response, HttpStatus.OK);
  }

}
