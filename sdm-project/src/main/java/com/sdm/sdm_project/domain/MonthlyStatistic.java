package com.sdm.sdm_project.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.YearMonth;

@Data
@NoArgsConstructor
public class MonthlyStatistic {
    private YearMonth month;
    private double earnings;
    private long singleRoomBookings;
    private long doubleRoomBookings;
    private long cancellations;
}

