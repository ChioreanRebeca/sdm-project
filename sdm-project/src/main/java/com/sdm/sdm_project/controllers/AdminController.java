package com.sdm.sdm_project.controllers;


import com.sdm.sdm_project.domain.Hotel;
import com.sdm.sdm_project.domain.Room;
import com.sdm.sdm_project.repositories.*;
import com.sdm.sdm_project.services.RoomService;
import org.hibernate.cfg.CacheSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private RoomService roomService;

    @Autowired
    private HotelRepository hotelRepository;

    @Autowired
    private ManagerRepository managerService;

    @Autowired
    private BookingRepository bookingService;

    @Autowired
    private TouristRepository touristService;

    @Autowired
    private CashierRepository cashierService;

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("rooms", roomService.findAll());
        model.addAttribute("hotels", hotelRepository.findAll());
        model.addAttribute("bookings", bookingService.findAll());
        model.addAttribute("tourists", touristService.findAll());
        model.addAttribute("managers", managerService.findAll());
        model.addAttribute("cashiers", cashierService.findAll());
        return "admin/dashboard";
    }


    @GetMapping("/rooms")
    public String viewRooms(Model model) {
        model.addAttribute("rooms", roomService.findAll());
        return "admin/room-list";
    }

    @GetMapping("/add-room")
    public String showAddRoomForm(Model model) {
        model.addAttribute("room", new Room());
        model.addAttribute("hotels", hotelRepository.findAll());

        model.addAttribute("error", null);

        return "admin/add-room";
    }

    @PostMapping("/add-room")
    public String addRoom(@ModelAttribute Room room, Model model, @RequestParam("hotelId")long hotelId) {
        if (roomService.roomNumberExists(room.getRoomNumber())) {
            model.addAttribute("error", "Duplicate room number");
            model.addAttribute("room", room);
            model.addAttribute("hotels", hotelRepository.findAll());
            return "admin/add-room";
        }

        Hotel hotel = hotelRepository.findById(hotelId).orElse(null);
        room.setHotel(hotel);

        room.setIsAvailable(true);
        roomService.save(room);
        return "redirect:/admin/rooms";
    }


}
