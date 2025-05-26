package com.sdm.sdm_project.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "Service")
@Data
@NoArgsConstructor
public class Service {
    @Id
    @SequenceGenerator(
            name = "service_seq",
            sequenceName = "service_seq",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "service_seq"
    )
    @Column(name = "id", updatable = false)
    private Long id;

    private String name;
    private Double price;
}
