package com.sdm.sdm_project.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity(name = "Tourist")
@Data
@NoArgsConstructor
public class Tourist {

    @Id
    @SequenceGenerator(
            name = "tourist_seq",
            sequenceName = "tourist_seq",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "tourist_seq"
    )
    @Column(name = "id", updatable = false)
    private long id;

    private String name;
    private String cnp;
    private String creditCardNumber;

    @OneToMany(mappedBy = "tourist")
    private List<Booking> bookings;
}
