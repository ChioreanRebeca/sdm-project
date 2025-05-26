package com.sdm.sdm_project.services;

import com.sdm.sdm_project.domain.Booking;
import com.sdm.sdm_project.domain.Payment;
import com.sdm.sdm_project.domain.Tourist;
import com.sdm.sdm_project.repositories.BookingRepository;
import com.sdm.sdm_project.repositories.PaymentRepository;
import com.sdm.sdm_project.repositories.TouristRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookingService {


    @Autowired
    private BookingRepository bookingRepository;

    public void cancelBooking(String bookingNumber) {
        Booking booking = bookingRepository.findByBookingNumber(bookingNumber)
                .orElseThrow(() -> new RuntimeException("Booking not found"));
        booking.setCancelationDate(LocalDate.now());
        booking.setCanceled(true);
        bookingRepository.save(booking);
    }

    public void extendBooking(String bookingNumber, LocalDate newCheckOutDate) {
        Booking booking = bookingRepository.findByBookingNumber(bookingNumber)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        if (booking.getCancelationDate() != null) {
            // Booking is canceled, skip conflict checking
            if (newCheckOutDate.isAfter(booking.getCheckOutDate())) {
                booking.setCheckOutDate(newCheckOutDate);
                bookingRepository.save(booking);
            } else {
                throw new IllegalArgumentException("New date must be after current check-out date");
            }
            return;
        }

        // Check if the room is available in the extended time
        List<Booking> conflicting = bookingRepository.findConflictingBookings(
                booking.getRoom().getId(),
                booking.getCheckOutDate(),
                newCheckOutDate
        );

        // Exclude the current booking from conflict check (for overlapping with itself)
        conflicting = conflicting.stream()
                .filter(b -> !b.getId().equals(booking.getId()))
                .collect(Collectors.toList());

        if (!conflicting.isEmpty()) {
            throw new IllegalStateException("Room is not available until the new check-out date.");
        }

        booking.setCheckOutDate(newCheckOutDate);
        bookingRepository.save(booking);
    }

    public Object getAllBookings() {
        return bookingRepository.findAll();
    }


    @Autowired
    private TouristRepository touristRepository;

   /* public boolean markAsPaid(String bookingId) {
        return bookingRepository.findByBookingNumber(bookingId).map(booking -> {
            booking.setPayed(true);
            bookingRepository.save(booking);
            return true;
        }).orElse(false);
    }
*/

    @Autowired
    private PaymentRepository paymentRepository;

    public List<Booking> getBookingsByCnp(String cnp) {
        Tourist tourist = touristRepository.findByCnp(cnp);
        if (tourist != null) {
            return bookingRepository.findAllByTourist(tourist);
        }
        return List.of();
    }


    public boolean markAsPaid(String bookingId) {
        Optional<Booking> optBooking = bookingRepository.findByBookingNumber(bookingId);
        if (optBooking.isEmpty()) {
            return false;
        }

        Booking booking = optBooking.get();

        if (booking.isPayed()) {
            return true;
        }

        // Calculate number of nights
        long nights = booking.getCheckOutDate().toEpochDay() - booking.getCheckInDate().toEpochDay();
        if (nights <= 0) {
            return false; // Invalid dates
        }

        // Calculate base price
        double basePrice = nights * booking.getRoom().getPrice();

        // Calculate add-on prices
        double addOnPrice = 0.0;
        if (booking.getBreakfast()) addOnPrice += 15.0 * nights;
        if (booking.getDinner())    addOnPrice += 25.0 * nights;
        if (booking.getInternet())  addOnPrice += 5.0  * nights;

        double totalPrice = basePrice + addOnPrice;

        // Optionally update booking totalPrice
        booking.setTotalPrice(totalPrice);

        // Mark as paid
        booking.setPayed(true);
        bookingRepository.save(booking);

        // Create and save payment
        Payment payment = new Payment();
        payment.setBooking(booking);
        payment.setAmount(totalPrice); // Now computed at payment time
        payment.setPaymentDate(LocalDate.now());

        paymentRepository.save(payment);

        return true;
    }


}
