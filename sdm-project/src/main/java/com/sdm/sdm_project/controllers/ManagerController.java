package com.sdm.sdm_project.controllers;

import com.sdm.sdm_project.services.ManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/manager")
public class ManagerController {

    @Autowired
    private ManagerService managerService;

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        int month = LocalDate.now().getMonthValue();
        int year = LocalDate.now().getYear();

        double earnings = managerService.getMonthlyEarnings(month, year);
        long canceledCount = managerService.getMonthlyCanceledCount(month, year);
        List<Object[]> popularRooms = managerService.getMostBookedRoomTypes();

        model.addAttribute("earnings", earnings);
        model.addAttribute("canceledCount", canceledCount);
        model.addAttribute("popularRooms", popularRooms);

        return "manager/dashboard";
    }
}

