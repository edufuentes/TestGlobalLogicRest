package com.globallogic.test.repository;

import com.globallogic.test.entity.User;
import org.springframework.data.repository.CrudRepository;

public interface IUserRepository extends CrudRepository<User, Long> {

}
