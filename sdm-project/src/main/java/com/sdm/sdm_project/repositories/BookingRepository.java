package com.sdm.sdm_project.repositories;

import com.sdm.sdm_project.domain.Booking;
import org.springframework.data.repository.CrudRepository;

public interface BookingRepository extends CrudRepository<Booking,Long> {
}
