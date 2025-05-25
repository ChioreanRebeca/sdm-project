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

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    public Tourist(String username, String password) {
        this.username = username;
        this.password = password;
    }


    @OneToMany(mappedBy = "tourist")
    private List<Booking> bookings;


    public Tourist(String name, String cnp, String creditCardNumber, String username, String password) {
        this.name = name;
        this.cnp = cnp;
        this.creditCardNumber = creditCardNumber;
        this.username = username;
        this.password = password;

    }
}
