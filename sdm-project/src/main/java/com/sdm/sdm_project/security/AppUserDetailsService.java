package com.sdm.sdm_project.security;


import com.sdm.sdm_project.repositories.AdminRepository;
import com.sdm.sdm_project.repositories.CashierRepository;
import com.sdm.sdm_project.repositories.ManagerRepository;
import com.sdm.sdm_project.repositories.TouristRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppUserDetailsService implements UserDetailsService {

    private final AdminRepository adminRepository;
    private final TouristRepository touristRepository;
    private final CashierRepository cashierRepository;
    private final ManagerRepository managerRepository;

    public AppUserDetailsService(AdminRepository adminRepository,
                                 TouristRepository touristRepository,
                                 CashierRepository cashierRepository,
                                 ManagerRepository managerRepository) {
        this.adminRepository = adminRepository;
        this.touristRepository = touristRepository;
        this.cashierRepository = cashierRepository;
        this.managerRepository = managerRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        var admin = adminRepository.findByUsername(username);
        if (admin.isPresent()) {
            return new User(
                    admin.get().getUsername(),
                    admin.get().getPassword(),
                    List.of(new SimpleGrantedAuthority("ROLE_ADMIN"))
            );
        }

        var tourist = touristRepository.findByUsername(username);
        if (tourist.isPresent()) {
            return new User(
                    tourist.get().getUsername(),
                    tourist.get().getPassword(),
                    List.of(new SimpleGrantedAuthority("ROLE_TOURIST"))
            );
        }

        var cashier = cashierRepository.findByUsername(username);
        if (cashier.isPresent()) {
            return new User(
                    cashier.get().getUsername(),
                    cashier.get().getPassword(),
                    List.of(new SimpleGrantedAuthority("ROLE_CASHIER"))
            );
        }

        var manager = managerRepository.findByUsername(username);
        if (manager.isPresent()) {
            return new User(
                    manager.get().getUsername(),
                    manager.get().getPassword(),
                    List.of(new SimpleGrantedAuthority("ROLE_MANAGER"))
            );
        }

        throw new UsernameNotFoundException("User not found with username: " + username);
    }
}
