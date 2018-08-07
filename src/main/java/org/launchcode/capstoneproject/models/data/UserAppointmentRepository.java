package org.launchcode.capstoneproject.models.data;

import org.launchcode.capstoneproject.models.UserAppointment;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserAppointmentRepository extends CrudRepository<UserAppointment, Integer> {

    //@Query("select a.appointtime from UserAppointment a, User b where b.userName=?1 and a.userName=b.userName")
   // public List<String> findByUserName(String username);

    public List<UserAppointment> findByStatus(boolean status);

    public UserAppointment findByAppointmentid(Long appointmentid);
}
