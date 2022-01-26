package com.example.AAiTStackOverflow.Controller;

import com.example.AAiTStackOverflow.Domain.Account;
import com.example.AAiTStackOverflow.Repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@Controller
@Slf4j
@AllArgsConstructor
@NoArgsConstructor

public class AccountController {

    @Autowired
    private UserRepository userRepo;

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new Account());
        return "accountCreate";
    }
    @PostMapping("/process_register")
    public String processRegister(@Valid @ModelAttribute("user") Account user,
                                  Errors errors, Model model) {
        if (errors.hasErrors()) {
            return "accountCreate";
        }

        if (userRepo.findByEmail(user.getEmail()) != null){
            String message = "Email already exists.";
            model.addAttribute("message", message);
            return "accountCreate";
        }

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        user.setRole(Account.Role.USER);
        userRepo.save(user);
        return "/login";
    }

    @GetMapping("About")
    public String aboutPage(){
        return "about";
    }

//    login controller

    @GetMapping("/login")
    public String login() {
        return "login";
    }

//        profile controller


    @GetMapping("/accountUpdate/{email}")
    public String updateAccount(@PathVariable String email, Model model){
        Account user = userRepo.findByEmail(email);
        model.addAttribute("user", user);
        return "accountUpdate";
    }
    @PostMapping("/accountUpdate/{email}")
    public String processUpdateAccount(@PathVariable String email,
                                       @Valid @ModelAttribute("user") Account user,
                                       Errors errors, Model model) {
        if (errors.hasErrors()) {
            return "accountUpdate";
        }

        for(Account account : userRepo.findAll()) {
            if (account.getEmail() == user.getEmail()) {
                String message = "Account found yea";
                model.addAttribute("message", message);
                return "accountUpdate";
            }
        }
        Account oldUser = userRepo.findByEmail(email);
        if(userRepo.findByEmail(user.getEmail()) != null ) {
            if (userRepo.findByEmail(user.getEmail()).getId() != oldUser.getId()) {
                String message = "Another person registered with this email";
                model.addAttribute("message", message);
                return "accountUpdate";
            }
        }

        oldUser.setId(oldUser.getId());
        oldUser.setFirstName(user.getFirstName());
        oldUser.setLastName(user.getLastName());
        oldUser.setEmail(user.getEmail());

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        oldUser.setPassword(encodedPassword);
        oldUser.setRole(Account.Role.USER);
        userRepo.save(oldUser);
        return "/login";
    }
}
