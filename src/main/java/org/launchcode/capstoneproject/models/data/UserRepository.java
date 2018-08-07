package org.launchcode.capstoneproject.models.data;

import org.launchcode.capstoneproject.models.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends CrudRepository<User, Integer> {

    public User findByUserName(String username);
}
