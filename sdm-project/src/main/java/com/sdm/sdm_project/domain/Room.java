package com.sdm.sdm_project.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;
import java.util.List;

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
    private Long id;

    private Integer roomNumber;

    @Enumerated(EnumType.STRING)
    private RoomType type;

    private Integer quality;
    private Boolean isAvailable;

    @Column(nullable = false)
    private Double price; // returns price per night

    @ManyToOne
    @JsonIgnore
    private Hotel hotel;

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Booking> bookings;


    public Room(Integer roomNumber, RoomType type, Integer quality,Double price, Boolean isAvailable, Hotel hotel) {
        this.roomNumber = roomNumber;
        this.type = type;
        this.quality = quality;
        this.price = price;
        this.isAvailable = isAvailable;
        this.hotel = hotel;
    }

    public Double getPrice() {
        return price;
    }


}

