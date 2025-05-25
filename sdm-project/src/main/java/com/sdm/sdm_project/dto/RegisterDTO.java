package com.sdm.sdm_project.dto;

import lombok.Data;

@Data
public class RegisterDTO {
    private String username;
    private String password;
    private String role; // "MANAGER", "TOURIST","CASHIER" ,"ADMIN"
}