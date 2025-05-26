package com.sdm.sdm_project.repositories;

import com.sdm.sdm_project.domain.Payment;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface PaymentRepository extends CrudRepository<Payment,Long> {

    @Query("SELECT SUM(p.amount) FROM Payment p WHERE FUNCTION('MONTH', p.paymentDate) = :month AND FUNCTION('YEAR', p.paymentDate) = :year")
    Double getMonthlyEarnings(@Param("month") int month, @Param("year") int year);

}
