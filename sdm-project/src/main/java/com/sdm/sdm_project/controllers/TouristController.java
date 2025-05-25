package com.sdm.sdm_project.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TouristController {
    @GetMapping("/tourist/dashboard")
    public String dashboard() {
        return "tourist/dashboard";
    }
}
