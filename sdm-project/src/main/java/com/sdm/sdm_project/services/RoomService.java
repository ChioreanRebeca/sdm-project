package com.sdm.sdm_project.services;

import com.sdm.sdm_project.domain.Room;
import com.sdm.sdm_project.repositories.BookingRepository;
import com.sdm.sdm_project.repositories.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RoomService {

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private BookingRepository bookingRepository;

    public void save(Room room) {
        roomRepository.save(room);
    }

    public boolean roomNumberExists(Integer roomNumber) {
        return roomRepository.findByRoomNumber(roomNumber).isPresent();
    }

    public Iterable<Room> findAll() {
        return roomRepository.findAll();
    }

    public List<Room> findAvailableRooms(LocalDate checkIn, LocalDate checkOut) {
        List<Room> allRooms = new ArrayList<>();
        roomRepository.findAll().forEach(allRooms::add);

        return allRooms.stream()
                .filter(room -> bookingRepository.findConflictingBookings(room.getId(), checkIn, checkOut).isEmpty())
                .collect(Collectors.toList());
    }

}
