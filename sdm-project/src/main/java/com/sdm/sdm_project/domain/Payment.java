package com.sdm.sdm_project.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private long id;

    private String paymentDate;
    private Double amount;
    private PaymentType paymentType;
    @OneToOne
    private Booking booking;
}

