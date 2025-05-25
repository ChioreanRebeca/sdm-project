package com.sdm.sdm_project.domain;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity(name = "Manager")
@NoArgsConstructor
public class Manager {

    @Id
    @SequenceGenerator(
            name = "manager_seq",
            sequenceName = "manager_seq",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "manager_seq"
    )
    @Column(name = "id", updatable = false)
    private long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    public Manager(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
