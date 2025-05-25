package com.sdm.sdm_project.dto;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
public class BookingDateForm {
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate checkInDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate checkOutDate;
}
