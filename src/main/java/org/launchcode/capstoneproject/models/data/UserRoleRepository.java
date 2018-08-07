package org.launchcode.capstoneproject.models.data;

import org.launchcode.capstoneproject.models.UserRole;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRoleRepository extends CrudRepository<UserRole, Integer> {
    @Query("select a.role from UserRole a, User b where b.userName=?1 and a.userid=b.userId")
    public List<String> findRoleByUserName(String username);
}
