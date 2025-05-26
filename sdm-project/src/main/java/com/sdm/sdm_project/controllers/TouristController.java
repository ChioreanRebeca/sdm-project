package com.sdm.sdm_project.controllers;

import com.sdm.sdm_project.domain.Booking;
import com.sdm.sdm_project.domain.Room;
import com.sdm.sdm_project.domain.Tourist;
import com.sdm.sdm_project.dto.BookingDateForm;
import com.sdm.sdm_project.repositories.BookingRepository;
import com.sdm.sdm_project.repositories.RoomRepository;
import com.sdm.sdm_project.repositories.TouristRepository;
import com.sdm.sdm_project.services.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;




@Controller
@RequestMapping("/tourist")
public class TouristController {

    @Autowired
    private RoomService roomService;

    @Autowired
    private BookingRepository bookingService;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private TouristRepository touristRepository;


    @GetMapping("/dashboard")
    public String dashboard(Principal principal, Model model) {
        Optional<Tourist> optionalTourist = touristRepository.findByUsername(principal.getName());
        Tourist tourist = optionalTourist.orElseThrow(() -> new RuntimeException("Tourist not found"));

        List<Booking> bookings = bookingService.findByTourist(tourist);
        model.addAttribute("bookings", bookings);

        return "tourist/dashboard";
    }

    @GetMapping("/book")
    public String showBookingForm(Model model) {
        BookingDateForm dateRange = new BookingDateForm(); // use your actual form class
        dateRange.setCheckInDate(LocalDate.now());
        dateRange.setCheckOutDate(LocalDate.now().plusDays(3));
        model.addAttribute("dates", dateRange);
        return "tourist/select-dates";
    }


    @PostMapping("/book")
    public String processDates(@ModelAttribute BookingDateForm dates, Model model) {
        List<Room> availableRooms = roomService.findAvailableRooms(dates.getCheckInDate(), dates.getCheckOutDate());
        if (availableRooms.isEmpty()) {
            model.addAttribute("error", "No rooms available for the selected dates.");
            return "tourist/select-dates";
        }
        model.addAttribute("rooms", availableRooms);
        model.addAttribute("dates", dates);
        return "tourist/choose-room";
    }

    @PostMapping("/confirm-booking")
    public String confirmBooking(@RequestParam Long roomId,
                                 @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkInDate,
                                 @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkOutDate,
                                 @RequestParam(required = false) boolean breakfast,
                                 @RequestParam(required = false) boolean dinner,
                                 @RequestParam(required = false) boolean internet,
                                 Principal principal,
                                 Model model) {

        Optional<Tourist> optionalTourist = touristRepository.findByUsername(principal.getName());
        Tourist tourist = optionalTourist.orElseThrow(() -> new RuntimeException("Tourist not found"));

        Room room = roomRepository.findById(roomId).orElseThrow();
        Booking booking = new Booking();

        // Set room and tourist
        booking.setRoom(room);
        booking.setTourist(tourist);

        // Set check-in and check-out dates
        booking.setCheckInDate(checkInDate);
        booking.setCheckOutDate(checkOutDate);

        // Set booking date as today
        booking.setBookingDate(LocalDate.now());

        // Calculate number of nights and total price
        long nights = checkOutDate.toEpochDay() - checkInDate.toEpochDay();
        double basePrice = nights * room.getPrice();

        double addOnPrice = 0.0;
        if (breakfast) addOnPrice += 15.0 * nights;
        if (dinner)    addOnPrice += 25.0 * nights;
        if (internet)  addOnPrice += 5.0  * nights;

        double totalPrice = basePrice + addOnPrice;

        booking.setBreakfast(breakfast);
        booking.setDinner(dinner);
        booking.setInternet(internet);
        booking.setTotalPrice(totalPrice);


        // Generate booking number: "BK" + zero-padded ID (after saving once to get ID)
        bookingService.save(booking); // Save once to generate ID
        String bookingNumber = String.format("BK%03d", booking.getId());
        booking.setBookingNumber(bookingNumber);

        // Save again with booking number
        bookingService.save(booking);

        model.addAttribute("booking", booking);
        return "tourist/booking-confirmation";
    }

    @PostMapping("/cancel-booking/{id}")
    public String cancelBooking(@PathVariable Long id, Principal principal) {
        Optional<Tourist> optionalTourist = touristRepository.findByUsername(principal.getName());
        Tourist tourist = optionalTourist.orElseThrow(() -> new RuntimeException("Tourist not found"));

        Booking booking = bookingService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Booking not found"));


        if (!booking.getTourist().getId().equals(tourist.getId())) {
            throw new SecurityException("Not authorized to cancel this booking");
        }

        booking.setCanceled(true);
        booking.setCancelationDate(LocalDate.now());
        bookingService.save(booking);

        return "redirect:/tourist/dashboard";
    }


}

