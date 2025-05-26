package com.sdm.sdm_project.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity(name = "Payment")
@Data
@NoArgsConstructor
public class Payment {

    @Id
    @SequenceGenerator(
            name = "payment_seq",
            sequenceName = "payment_seq",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "payment_seq"
    )
    @Column(name = "id", updatable = false)
    private Long id;

    private LocalDate paymentDate;
    private Double amount;
    private PaymentType paymentType;

    @ManyToOne
    @JsonIgnore
    private Booking booking;
}

