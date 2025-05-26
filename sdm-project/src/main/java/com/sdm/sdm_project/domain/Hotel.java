package com.sdm.sdm_project.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Entity(name = "Hotel")
@NoArgsConstructor
public class Hotel {
    @Id
    @SequenceGenerator(
            name = "hotel_seq",
            sequenceName = "hotel_seq",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "hotel_seq"
    )
    @Column(name = "id", updatable = false)
    private Long id;

private String name;
private String address;

@Column(nullable = false)
private Double stars=0.0;
private Integer numberOfRooms;

    @OneToMany(mappedBy = "hotel", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties("hotel")
    private List<Room> rooms;


    public Hotel(String name, String address, Double stars, Integer numberOfRooms) {
        this.name = name;
        this.address = address;
        this.stars = stars;
        this.numberOfRooms = numberOfRooms;
    }
}
