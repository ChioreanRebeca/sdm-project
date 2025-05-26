package com.sdm.sdm_project.repositories;

import com.sdm.sdm_project.domain.Tourist;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface TouristRepository extends CrudRepository<Tourist,Long> {
    Optional<Tourist> findByUsername(String username);

    Tourist findByCnp(String cnp);
}
