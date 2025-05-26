package com.sdm.sdm_project.repositories;

import com.sdm.sdm_project.domain.Booking;
import com.sdm.sdm_project.domain.Tourist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface BookingRepository extends CrudRepository<Booking,Long> {

    /*@Query("SELECT b FROM Booking b WHERE b.room.id = :roomId AND " +
            "(b.checkInDate < :checkOut AND b.checkOutDate > :checkIn)")
    List<Booking> findConflictingBookings(@Param("roomId") Long roomId,
                                          @Param("checkIn") LocalDate checkIn,
                                          @Param("checkOut") LocalDate checkOut);*/

    @Query("SELECT b FROM Booking b WHERE b.room.id = :roomId " +
            "AND b.canceled = false " +
            "AND b.checkOutDate > :checkInDate AND b.checkInDate < :checkOutDate")
    List<Booking> findConflictingBookings(@Param("roomId") Long roomId,
                                          @Param("checkInDate") LocalDate checkInDate,
                                          @Param("checkOutDate") LocalDate checkOutDate);

    List<Booking> findByTourist(Tourist tourist);

    Optional<Booking> findById(Long id);

    Optional<Booking> findByBookingNumber(String bookingNumber);

    List<Booking> findAllByTourist(Tourist tourist);

    @Query("SELECT b.room.type, COUNT(b) FROM Booking b WHERE b.canceled = false GROUP BY b.room.type ORDER BY COUNT(b) DESC")
    List<Object[]> getMostBookedRoomTypes();

    @Query("SELECT COUNT(b) FROM Booking b WHERE FUNCTION('MONTH', b.cancelationDate) = :month AND FUNCTION('YEAR', b.cancelationDate) = :year AND b.canceled = true")
    Long getMonthlyCanceledCount(@Param("month") int month, @Param("year") int year);


}
