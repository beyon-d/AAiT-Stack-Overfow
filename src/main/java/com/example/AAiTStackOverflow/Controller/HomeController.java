package com.example.AAiTStackOverflow.Controller;

import com.example.AAiTStackOverflow.Domain.Account;
import com.example.AAiTStackOverflow.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class HomeController {
    @Autowired
    private UserRepository userRepo;

    @GetMapping("/")
    public String viewHomePage(Model model, Authentication authentication) {
        if (authentication != null){
            UserDetails userPrincipal = (UserDetails) authentication.getPrincipal();
            Account author = userRepo.findByEmail( userPrincipal.getUsername());
            String role = author.getRole().toString();
            model.addAttribute("role", role);
            model.addAttribute("author", author);

            return "home";
        }
        return "home";
    }

    @GetMapping("/users")
    public String listUsers(Model model) {
        List<Account> listUsers = userRepo.findAll();
        model.addAttribute("listUsers", listUsers);
        return "users";
    }
}
