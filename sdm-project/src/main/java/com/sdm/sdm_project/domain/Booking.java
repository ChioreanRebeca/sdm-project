package com.sdm.sdm_project.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity(name = "Booking")
@Data
@NoArgsConstructor
public class Booking {
    @Id
    @SequenceGenerator(
            name = "booking_generator_seq",
            sequenceName ="booking_generator_seq",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "booking_generator_seq"
    )
    private long id;

    private String bookingNumber;
    private String bookingDate;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private LocalDate cancelationDate;
    private Double totalPrice;

    @ManyToOne
    private Tourist tourist;

    @ManyToOne
    private Room room;

    @ManyToMany
    private List<Service> services;
}
