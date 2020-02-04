package com.globallogic.test.service;

import com.globallogic.test.entity.User;
import com.globallogic.test.repository.IUserRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * User Service Implementation.
 */
@Service
public class UserServiceImpl implements UserService {

  /**
   * Autowiring the User Repository.
   */
  @Autowired
  private IUserRepository userRepository;

  /**
   * Get All Users.
   * @return List of all planets.
   */
  @Override
  public List<User> getAllUsers() {
    return (List<User>) userRepository.findAll();
  }

  /**
   * Get User By Id.
   * @param id Id
   * @return User
   */
  @Override
  public User getUserById(final Long id) {
    return userRepository.findById(id).get();
  }

  /**
   * Save User.
   * @param user User to save
   * @return Saved User
   */
  @Override
  public User saveUser(final User user) {
    return userRepository.save(user);
  }

  /**
   * Update User.
   * @param id Id
   * @param userToUpdate User to Update
   * @return Updated User
   */
  @Override
  public User updateUserById(
          final Long id, final User userToUpdate) {
    // Fetch the User from db
    User userFromDb = userRepository.findById(id).get();
    userFromDb.setName(userToUpdate.getName());
   // userFromDb.setNumberOfMoons(planetToUpdate.getNumberOfMoons());
    return userRepository.save(userFromDb);
  }

  /**
   * Delete User by Id.
   * @param id Id
   */
  @Override
  public void deleteUserById(final Long id) {
    userRepository.deleteById(id);
  }

  /**
   * Search User by name containing.
   * @param searchString SearchString
   * @return Search result
   */
 /* @Override
  public List<User> getUserByNameContaining(final String searchString) {
    return userRepository.findByNameContaining(searchString);
  }
*/
  /**
   * Search User by name like.
   * @param searchString SearchString
   * @return Search result
   */
 /* @Override
  public List<User> getUserByNameLike(final String searchString) {
    return userRepository.findByNameLike(searchString);
  }*/
}
