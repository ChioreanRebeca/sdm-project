package com.sdm.sdm_project.controllers;

import com.sdm.sdm_project.dto.RegisterDTO;
import com.sdm.sdm_project.services.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthController {

    @Autowired
    private RegistrationService registrationService;

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("user", new RegisterDTO());
        return "register";
    }

    @GetMapping("/login")
    public String showLogin(Model model) {
        model.addAttribute("user", new RegisterDTO());
        return "login";
    }

    @PostMapping("/register")
    public String processRegistration(@ModelAttribute("user") RegisterDTO dto) {
        registrationService.register(dto);
        return "redirect:/login";
    }
}
