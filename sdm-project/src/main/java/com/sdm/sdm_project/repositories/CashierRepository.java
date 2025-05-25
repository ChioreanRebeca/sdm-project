package com.sdm.sdm_project.repositories;

import com.sdm.sdm_project.domain.Cashier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CashierRepository extends CrudRepository<Cashier,Long> {
    Optional<Cashier> findByUsername(String username);
}
