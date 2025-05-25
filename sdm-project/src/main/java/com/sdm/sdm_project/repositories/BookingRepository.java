package com.sdm.sdm_project.repositories;

import com.sdm.sdm_project.domain.Booking;
import com.sdm.sdm_project.domain.Tourist;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface BookingRepository extends CrudRepository<Booking,Long> {

    @Query("SELECT b FROM Booking b WHERE b.room.id = :roomId AND " +
            "(b.checkInDate < :checkOut AND b.checkOutDate > :checkIn)")
    List<Booking> findConflictingBookings(@Param("roomId") Long roomId,
                                          @Param("checkIn") LocalDate checkIn,
                                          @Param("checkOut") LocalDate checkOut);

    List<Booking> findByTourist(Tourist tourist);
}
