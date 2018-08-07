package org.launchcode.capstoneproject.controllers;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ProjectController {



  /*  @RequestMapping(value="user/signup")
    public ModelAndView register() {
        return new ModelAndView("signup", "user", new User());
    }*/

   /* @RequestMapping(value="doctor/signin")
    public String d(){
        return "Doctor/signin";
    }*/

    @RequestMapping(value="")
    public String index(){
        return "home";
    }

    @RequestMapping(value="doctor/index")
    public String di(){
        return "Doctor/index";
    }

    @RequestMapping(value="doctor/calendar")
    public String cal(){
        return "Doctor/calendar";
    }

    @RequestMapping(value="user/welcome")
    public String a(){
        return "user/welcome";
    }


    public static void main(String[] args){
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        System.out.println("Encoded doctor : "+bCryptPasswordEncoder.encode("doctor"));

        System.out.println("Encoded patient : "+bCryptPasswordEncoder.encode("patient"));
    }
}
