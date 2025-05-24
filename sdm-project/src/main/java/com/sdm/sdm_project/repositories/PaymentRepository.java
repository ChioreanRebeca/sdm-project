package com.sdm.sdm_project.repositories;

import com.sdm.sdm_project.domain.Payment;
import org.springframework.data.repository.CrudRepository;

public interface PaymentRepository extends CrudRepository<Payment,Long> {
}
