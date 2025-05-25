package com.sdm.sdm_project.repositories;

import com.sdm.sdm_project.domain.Admin;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface AdminRepository extends CrudRepository<Admin, Integer> {
    Optional<Admin> findByUsername(String username);
}
