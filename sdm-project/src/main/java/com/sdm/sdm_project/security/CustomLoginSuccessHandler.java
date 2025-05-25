package com.sdm.sdm_project.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomLoginSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication)
            throws IOException {
        String redirectURL = request.getContextPath();

        for (GrantedAuthority auth : authentication.getAuthorities()) {
            String role = auth.getAuthority();
            if (role.equals("ROLE_CASHIER")) {
                redirectURL = "/cashier/dashboard";
                break;
            } else if (role.equals("ROLE_MANAGER")) {
                redirectURL = "/manager/dashboard";
                break;
            } else if (role.equals("ROLE_ADMIN")) {
                redirectURL = "/admin/dashboard";
                break;
            } else if (role.equals("ROLE_TOURIST")) {
            redirectURL = "/tourist/dashboard";
            break;
        }
        }

        System.out.println("Redirect URL: " + redirectURL);
        response.sendRedirect(redirectURL);
    }
}
