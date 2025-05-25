package com.sdm.sdm_project.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ManagerController {
    @GetMapping("/manager/dashboard")
    public String dashboard() {
        return "manager/dashboard";
    }
}
