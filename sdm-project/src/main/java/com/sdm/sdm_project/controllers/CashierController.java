package com.sdm.sdm_project.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CashierController {

    @GetMapping("/cashier/dashboard")
    public String dashboard() {
        return "cashier/dashboard";
    }
}
