package com.sdm.sdm_project.controllers;

import com.sdm.sdm_project.domain.Booking;
import com.sdm.sdm_project.repositories.BookingRepository;
import com.sdm.sdm_project.services.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("/cashier")
public class CashierController {



    @Autowired
    private BookingService bookingService;

    @Autowired
    private BookingRepository bookingRepository;

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("bookings", bookingService.getAllBookings());
        return "cashier/dashboard";
    }

    @PostMapping("/cancel/{bookingNumber}")
    public ResponseEntity<String> cancelBooking(@PathVariable String bookingNumber) {
        bookingService.cancelBooking(bookingNumber);
        return ResponseEntity.ok("Booking canceled successfully");
    }

    @PostMapping("/extend/{bookingNumber}")
    public ResponseEntity<String> extendBooking(@PathVariable String bookingNumber,
                                                    @RequestBody Map<String, String> payload) {
        LocalDate newDate = LocalDate.parse(payload.get("newCheckOutDate"));
        bookingService.extendBooking(bookingNumber, newDate);
        return ResponseEntity.ok("Booking extended successfully");
    }

    @GetMapping("/bookings")
    @ResponseBody
    public Iterable<Booking> getAllCurrentBookings() {
        return bookingRepository.findAll();
    }




    @PostMapping("/pay/{bookingId}")
    public ResponseEntity<String> markBookingAsPaid(@PathVariable String bookingId) {
        boolean updated = bookingService.markAsPaid(bookingId);
        return updated
                ? ResponseEntity.ok("Booking marked as paid.")
                : ResponseEntity.badRequest().body("Booking not found.");
    }

    @GetMapping("/bookings/by-cnp/{cnp}")
    @ResponseBody
    public List<Booking> getBookingsByCnp(@PathVariable String cnp) {
        return bookingService.getBookingsByCnp(cnp);
    }


}
