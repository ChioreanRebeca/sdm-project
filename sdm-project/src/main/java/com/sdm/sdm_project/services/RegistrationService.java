package com.sdm.sdm_project.services;

import com.sdm.sdm_project.domain.Admin;
import com.sdm.sdm_project.domain.Cashier;
import com.sdm.sdm_project.domain.Manager;
import com.sdm.sdm_project.domain.Tourist;
import com.sdm.sdm_project.dto.RegisterDTO;
import com.sdm.sdm_project.repositories.AdminRepository;
import com.sdm.sdm_project.repositories.CashierRepository;
import com.sdm.sdm_project.repositories.ManagerRepository;
import com.sdm.sdm_project.repositories.TouristRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegistrationService {

    @Autowired
    private TouristRepository touristRepository;

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private CashierRepository cashierRepository;

    @Autowired
    private ManagerRepository managerRepository;


    public void register(RegisterDTO dto) {
        switch (dto.getRole().toUpperCase()) {
            case "TOURIST":
                Tourist tourist = new Tourist(dto.getUsername(), dto.getPassword());
                touristRepository.save(tourist);
                break;

            case "CASHIER":
                Cashier cashier = new Cashier(dto.getUsername(), dto.getPassword());
                cashierRepository.save(cashier);
                break;

            case "MANAGER":
                Manager manager = new Manager(dto.getUsername(),dto.getPassword());
                managerRepository.save(manager);
                break;

            case "ADMIN":
                Admin admin = new Admin(dto.getUsername(), dto.getPassword());
                adminRepository.save(admin);
                break;

            default:
                throw new IllegalArgumentException("Invalid role: " + dto.getRole());
        }
    }
}