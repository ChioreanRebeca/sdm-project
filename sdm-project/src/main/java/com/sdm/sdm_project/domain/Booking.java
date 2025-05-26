package com.sdm.sdm_project.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
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
    private Long id;

    private String bookingNumber;
    private LocalDate bookingDate;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;

    @Column
    private LocalDate cancelationDate;
    private Double totalPrice;

    private Boolean breakfast = false;
    private Boolean dinner = false;
    private Boolean internet = false;

    @ManyToOne
    @JsonIgnoreProperties("bookings")
    private Tourist tourist;

    @ManyToOne
    private Room room;

    @ManyToMany
    private List<Service> services;

    @OneToMany(mappedBy = "booking",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<Payment> payments = new ArrayList<>();


    private boolean canceled = false;

    @JsonProperty("canceled")
    public boolean isCanceled() {
        return canceled;
    }

    public void setCanceled(boolean canceled) {
        this.canceled = canceled;
    }

    private boolean payed = false;

    @JsonProperty("payed")
    public boolean isPayed() {
        return payed;
    }

    public void setPayed(boolean payed) {
        this.payed = payed;
    }



}
