package org.launchcode.capstoneproject.controllers;

import org.launchcode.capstoneproject.models.UserAppointment;
import org.launchcode.capstoneproject.models.UserAppointmentSelection;
import org.launchcode.capstoneproject.models.data.UserAppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
@RequestMapping("/appointments")
public class AppointmentController {

    @Autowired
    private UserAppointmentRepository userAppointmentRepository;

    @RequestMapping(value = "/getAllAvailableSlots")
    public String getAllAvailableAppointments(Model model) {

        List<UserAppointment> availableSlots = userAppointmentRepository.findByStatus(Boolean.valueOf("0").booleanValue());

        for (UserAppointment userAppointment : availableSlots) {
            System.out.println("Available: " + userAppointment.getAppointtime() + " : " + userAppointment.getUsername());
        }

        model.addAttribute("userAppointments", availableSlots);
        return "availableSlots";
    }

    @RequestMapping(value = "/schedule", method = RequestMethod.GET)
    public String scheduleGET(Model model) {

        List<UserAppointment> availableSlots = userAppointmentRepository.findByStatus(Boolean.valueOf("0").booleanValue());

        for (UserAppointment userAppointment : availableSlots) {
            System.out.println("Available: " + userAppointment.getAppointtime() + " : " + userAppointment.getUsername());
        }

        model.addAttribute("userAppointments", availableSlots);
        model.addAttribute("userAppointmentSelection",new UserAppointmentSelection());
        return "schedule";
    }

    @RequestMapping(value = "/schedule", method = RequestMethod.POST)
    @Transactional
    public void schedulePOST(@ModelAttribute("userAppointmentSelection") UserAppointmentSelection userAppointmentSelection) {

       System.out.println("Chosen slot id: "+userAppointmentSelection.getId());
        Long chosenappointmentid = Long.valueOf(userAppointmentSelection.getId());
        UserAppointment selectedAppointment = userAppointmentRepository.findByAppointmentid(chosenappointmentid);

       selectedAppointment.setStatus(Boolean.TRUE);

       userAppointmentRepository.save(selectedAppointment);

        UserAppointment selectedAppointment1 = userAppointmentRepository.findByAppointmentid(chosenappointmentid);
        System.out.println("POST Update selectedAppointment1: "+selectedAppointment1);

    }

    @RequestMapping(value = "calendar")
    public String displayDoctorCal(Model model) {
        model.addAttribute("title", "Welcome");
        return "Doctor/calendar";
    }
}
