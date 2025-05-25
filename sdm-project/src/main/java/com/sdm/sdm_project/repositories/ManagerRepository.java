package com.sdm.sdm_project.repositories;

import com.sdm.sdm_project.domain.Manager;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ManagerRepository extends CrudRepository<Manager, Integer> {
    Optional<Manager> findByUsername(String username);
}
