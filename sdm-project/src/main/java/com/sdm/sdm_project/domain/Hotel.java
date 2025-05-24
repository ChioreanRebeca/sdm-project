package com.sdm.sdm_project.domain;

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
    private long id;

private String name;
private String address;
private Double stars;
private Integer numberOfRooms;

    @OneToMany(mappedBy = "hotel")
    private List<Room> rooms;
}
