package org.launchcode.capstoneproject.controllers;

import org.launchcode.capstoneproject.models.UserAppointment;
import org.launchcode.capstoneproject.models.UserAppointmentSelection;
import org.launchcode.capstoneproject.models.data.UserAppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/appointments")
public class AppointmentController {


    @Autowired
    private UserAppointmentRepository userAppointmentRepository;

    private static final SimpleDateFormat dateFormatter = new SimpleDateFormat("MM/dd/yyyy HH:mm");


    @RequestMapping(value = "createappointment", method = RequestMethod.GET)
    public String createNewAppointment(Model model, @RequestParam(value = "error", required = false) String error){
        model.addAttribute("title", "Create New Appointment");
        model.addAttribute("newAppointment", new UserAppointment());
        model.addAttribute("error", error);
        return "newAppointment";
    }


    @RequestMapping(value = "createappointment", method = RequestMethod.POST)
    public String createNewAppointment(Principal principal, @ModelAttribute UserAppointment userAppointment){

        userAppointment.setUsername(principal.getName());
        userAppointment.setStatus(Boolean.FALSE);

        System.out.println("[AppointmentController.createNewAppointment] new appointment : "+ userAppointment);

        try {
            UserAppointment savedNewAppointment = userAppointmentRepository.save(userAppointment);
            System.out.println("[AppointmentController.createNewAppointment] savedNewAppointment : "+ savedNewAppointment);

        }catch(Exception e){
            System.out.println("[AppointmentController.createNewAppointment] Failed to book : "+ e.getMessage());
            return "redirect:/appointments/createappointment?error=true";
        }

        return "redirect:/appointments/getAllAvailableSlots";
    }


    @RequestMapping(value = "/getAllAvailableSlots")
    public String getAllAvailableAppointments(Model model) throws ParseException {

        List<UserAppointment> allAppointments = getAllAppointments();
        model.addAttribute("userAppointments", allAppointments);
        return "availableSlots";
    }


    @RequestMapping(value = "/schedule", method = RequestMethod.GET)
    public String scheduleGET(Principal principal, Model model, @RequestParam(value="myNewAppointmentTime", required = false) String myNewAppointmentTime) throws ParseException {

        String username = principal.getName();
        String myExistingScheduledAppointmentTime = "";
        List<UserAppointment> eligibleAppoinmentSlots = new ArrayList<UserAppointment>();
        UserAppointmentSelection userAppointmentSelection =  new UserAppointmentSelection();

        List<UserAppointment> myScheduledAppointment = userAppointmentRepository.findByUsernameAndStatus(username, Boolean.TRUE);

        if(!StringUtils.hasText(myNewAppointmentTime) && myScheduledAppointment != null && !myScheduledAppointment.isEmpty()){
            myExistingScheduledAppointmentTime = myScheduledAppointment.get(0).getAppointtime();
            System.out.println("[AppointmentController.scheduleGET] User: "+principal.getName() + " has already scheduled an appointment for date and time: "+myExistingScheduledAppointmentTime);

            userAppointmentSelection.setMyExistingAppointmentTime(myExistingScheduledAppointmentTime);
        }else{
            eligibleAppoinmentSlots = getEligibleAppointmentForSchedule();
        }

        model.addAttribute("myNewAppointmentTime", myNewAppointmentTime);
        model.addAttribute("userAppointments", eligibleAppoinmentSlots);
        model.addAttribute("userAppointmentSelection", userAppointmentSelection);
        return "schedule";
    }

    @RequestMapping(value = "/schedule", method = RequestMethod.POST)
    @Transactional
    public String schedulePOST(Principal principal, @ModelAttribute("userAppointmentSelection") UserAppointmentSelection userAppointmentSelection) {

        String myExistingScheduledAppointmentTime = "";

        System.out.println("[AppointmentController.schedulePOST] Received request for appointment for user " +principal.getName());
        System.out.println("[AppointmentController.schedulePOST] Checking if user has already one scheduled appointment or not ");
        System.out.println("Chosen slot id: "+userAppointmentSelection.getId());

        List<UserAppointment> myScheduledAppointment = userAppointmentRepository.findByUsernameAndStatus(principal.getName(), Boolean.TRUE);

        if(myScheduledAppointment != null && !myScheduledAppointment.isEmpty()){
            myExistingScheduledAppointmentTime = myScheduledAppointment.get(0).getAppointtime();

            System.out.println("[AppointmentController.schedulePOST] User: "+principal.getName() + " has already scheduled an appointment for date and time: "+myExistingScheduledAppointmentTime);
            return "redirect:/appointments/schedule?myExistingAppointmentTime="+myExistingScheduledAppointmentTime;

        }else{
            Long chosenappointmentid = Long.valueOf(userAppointmentSelection.getId());
            UserAppointment selectedAppointment = userAppointmentRepository.findByAppointmentid(chosenappointmentid);

            System.out.println("[AppointmentController.schedulePOST] User: "+principal.getName() + " does not have an already scheduled an appointment. so proceeding to book a new appointment for date and time: "+selectedAppointment.getAppointtime());


            selectedAppointment.setStatus(Boolean.TRUE);   //this will set status to 1
            selectedAppointment.setUsername(principal.getName());
            userAppointmentRepository.save(selectedAppointment);

            UserAppointment myNewAppointment = userAppointmentRepository.findByAppointmentid(chosenappointmentid);
            System.out.println("POST Update selectedAppointment1: "+myNewAppointment);

            return "redirect:/appointments/schedule?myNewAppointmentTime="+myNewAppointment.getAppointtime();
            }
    }

    @RequestMapping(value = "calendar")
    public String displayDoctorCal(Model model) {
        model.addAttribute("title", "Welcome");
        return "Doctor/calendar";
    }

    private List<UserAppointment> getEligibleAppointmentForSchedule() throws ParseException {
        List<UserAppointment> allAvailableSlots = userAppointmentRepository.findByStatus(Boolean.valueOf("0").booleanValue());
        List<UserAppointment> eligibleAppoinmentSlots = new ArrayList<UserAppointment>();

        Date currentDate = Calendar.getInstance().getTime();

        for (UserAppointment userAppointment : allAvailableSlots) {
            System.out.println("Available appointment slot: " + userAppointment.getAppointtime());

            Date availableAppointmentTime = dateFormatter.parse(userAppointment.getAppointtime());

            if(availableAppointmentTime.after(currentDate)){
                System.out.println("This appointment slot is eligible to be scheduled as it is after current date time");
                eligibleAppoinmentSlots.add(userAppointment);
            }else{
                System.out.println("This appointment slot is NOT eligible to be scheduled as it is a past date than current date time");
            }
        }
        return eligibleAppoinmentSlots;
    }

    private List<UserAppointment> getAllAppointments() throws ParseException {
        List<UserAppointment> allAvailableSlots = userAppointmentRepository.findAll();
        List<UserAppointment> eligibleAppoinmentSlots = new ArrayList<UserAppointment>();

        Date currentDate = Calendar.getInstance().getTime();

        for (UserAppointment userAppointment : allAvailableSlots) {
            System.out.println(" appointment slot: " + userAppointment);

            Date availableAppointmentTime = dateFormatter.parse(userAppointment.getAppointtime());

            if(availableAppointmentTime.before(currentDate) && userAppointment.getUsername().equalsIgnoreCase("doctor")){
                System.out.println("This appointment slot is NOT eligible to be scheduled as it is past due and un-used and hence not displaying");
            }else{
                eligibleAppoinmentSlots.add(userAppointment);
                System.out.println("This appointment slot is eligible to be scheduled or already scheduled by one of patient");
            }
        }
        return eligibleAppoinmentSlots;
    }
}
