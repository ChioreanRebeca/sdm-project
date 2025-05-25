package com.sdm.sdm_project.repositories;

import com.sdm.sdm_project.domain.Room;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface RoomRepository extends CrudRepository<Room,Long> {
    Optional<Object> findByRoomNumber(Integer roomNumber);
}
