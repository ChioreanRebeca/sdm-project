package com.sdm.sdm_project.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "Room")
@Data
@NoArgsConstructor
public class Room {

    @Id
    @SequenceGenerator(
            name = "room_seq",
            sequenceName = "room_seq",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "room_seq"
    )
    @Column(name = "id", updatable = false)
    private long id;

    private Integer roomNumber;
    @Enumerated(EnumType.STRING)
    private RoomType type;

    private Integer quality;
    private Boolean isAvailable;

    @ManyToOne
    private Hotel hotel;

}

