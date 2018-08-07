package org.launchcode.capstoneproject.controllers;


import org.launchcode.capstoneproject.models.User;
import org.launchcode.capstoneproject.models.UserRole;
import org.launchcode.capstoneproject.models.data.UserRepository;
import org.launchcode.capstoneproject.models.data.UserRoleRepository;
import org.launchcode.capstoneproject.models.forms.RegisterForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
@RequestMapping("user")
public class UserController {


    @Autowired
    UserRepository userRepository;

    @Autowired
    UserRoleRepository userRoleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @RequestMapping(value="dashboard")
    public String home(){
        return "dashboard";
    }

    @RequestMapping(value="register", method=RequestMethod.GET)
    public String register(Model model) {

        model.addAttribute("title", "New User Signup");
        model.addAttribute(new RegisterForm());
        return "register";
    }

    @RequestMapping(value="register", method = RequestMethod.POST)
    public String register(@ModelAttribute @Valid RegisterForm registerForm, Errors errors, HttpServletRequest request) {

        if (errors.hasErrors()) {
            return "register";
        }

        User existingUser = userRepository.findByUserName(registerForm.getUsername());

        if (existingUser != null) {
            errors.rejectValue("username", "username.alreadyexists", "This name is already taken. Please try another name");
            return "register";
        }

        User newUser = new User(registerForm.getUsername(), passwordEncoder.encode(registerForm.getPassword()),"test@gmail.com",1);

        User savedUser = registerUser(newUser);

        System.out.println("savedUser : "+savedUser);

        System.out.print("user name : "+ registerForm.getUsername());

        assignRole("ROLE_MYPROFILE", registerForm.getUsername());
        assignRole("ROLE_SCHEDULE_APPOINTMENT",registerForm.getUsername());
        assignRole("ROLE_VIEW_APPOINTMENT",registerForm.getUsername());

        return "redirect:dashboard";
    }

    @Transactional(propagation= Propagation.REQUIRES_NEW)
    private User registerUser(User newUser) {
        return userRepository.save(newUser);
    }

    @Transactional(propagation= Propagation.REQUIRES_NEW)
    private UserRole assignRole(String roleName, String username) {

        System.out.println("username : "+username);
        System.out.println("roleName : "+roleName);

        UserRole userRole = new UserRole();
        userRole.setRole(roleName);
        userRole.setUsername(username);

        return userRoleRepository.save(userRole);
    }
}
