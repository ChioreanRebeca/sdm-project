package com.sdm.sdm_project.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity(name = "Cashier")
@NoArgsConstructor
public class Cashier {
    @Id
    @SequenceGenerator(
            name = "cashier_seq",
            sequenceName = "cashier_seq",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "cashier_seq"
    )
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    public Cashier(String username, String password) {
        this.username = username;
        this.password = password;
    }


}
