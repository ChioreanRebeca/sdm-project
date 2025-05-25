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
        model.addAttribute("dates", new BookingDateForm());
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
                                 Principal principal,
                                 Model model) {
        Optional<Tourist> optionalTourist = touristRepository.findByUsername(principal.getName());
        Tourist tourist = optionalTourist.orElseThrow(() -> new RuntimeException("Tourist not found"));

        Room room = roomRepository.findById(roomId).orElseThrow();
        Booking booking = new Booking();
        booking.setRoom(room);
        booking.setTourist(tourist);
        booking.setCheckInDate(checkInDate);
        booking.setCheckOutDate(checkOutDate);
        bookingService.save(booking);

        model.addAttribute("booking", booking);
        return "tourist/booking-confirmation";
    }
}

