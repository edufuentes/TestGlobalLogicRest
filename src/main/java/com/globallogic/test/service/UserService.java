package com.globallogic.test.service;

import com.globallogic.test.entity.User;

import java.util.List;

/**
 * Planet Service.
 */
public interface UserService {

  /**
   * Get All Planets.
   * @return List of all planets.
   */
  List<User> getAllUsers();

  /**
   * Get Planet By Id.
   * @param id Id
   * @return Planet
   */
  User getUserById(Long id);

  /**
   * Save Planet.
   * @param user  to save
   * @return Saved User
   */
  User saveUser(User user);

  /**
   * Update Planet.
   * @param id Id
   * @param user User to Update
   * @return Updated User
   */
  User updateUserById(Long id, User user);

  /**
   * Delete User by Id.
   * @param id Id
   */
  void deleteUserById(Long id);

  /**
   * Search User by name containing.
   * @param searchString SearchString
   * @return Search result
   */
 // List<Planet> getPlanetByNameContaining(String searchString);

  /**
   * Search Planet by name like.
   * @param searchString SearchString
   * @return Search result
   */
 // List<Planet> getPlanetByNameLike(String searchString);
}
