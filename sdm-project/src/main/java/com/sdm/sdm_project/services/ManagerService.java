package com.sdm.sdm_project.services;

import com.sdm.sdm_project.repositories.BookingRepository;
import com.sdm.sdm_project.repositories.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ManagerService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private BookingRepository bookingRepository;

    public double getMonthlyEarnings(int month, int year) {
        return Optional.ofNullable(paymentRepository.getMonthlyEarnings(month, year)).orElse(0.0);
    }

    public List<Object[]> getMostBookedRoomTypes() {
        return bookingRepository.getMostBookedRoomTypes();
    }

        public long getMonthlyCanceledCount(int month, int year) {
        return bookingRepository.getMonthlyCanceledCount(month, year);
    }
}
