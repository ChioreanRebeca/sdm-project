package com.sdm.sdm_project;

import com.sdm.sdm_project.domain.*;
import com.sdm.sdm_project.repositories.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.util.List;

@SpringBootApplication
public class SdmProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(SdmProjectApplication.class, args);
	}

	@Bean
	CommandLineRunner commandLineRunner(
			AdminRepository adminRepository,
			CashierRepository cashierRepository,
			ManagerRepository managerRepository,
			TouristRepository touristRepository,
			HotelRepository hotelRepository,
			RoomRepository roomRepository,
			BookingRepository bookingRepository,
			ServiceRepository serviceRepository,
			PaymentRepository paymentRepository
	) {
		return args -> {
			// Admin
			Admin admin = new Admin("admin", "123");
			adminRepository.save(admin);

			// Cashier
			Cashier cashier = new Cashier("cashier1", "123");
			cashierRepository.save(cashier);

			// Manager
			Manager manager = new Manager("manager1", "123");
			managerRepository.save(manager);

			// Tourist
			Tourist tourist = new Tourist("John Doe", "1234567890123", "4111111111111111", "tourist1", "123");
			touristRepository.save(tourist);

			// Hotel and Rooms
			Hotel hotel = new Hotel();
			hotel.setName("Palm Beach Hotel");
			hotel.setAddress("Seaside Blvd 42");
			hotel.setStars(4.0);
			hotel.setNumberOfRooms(100);
			hotelRepository.save(hotel);

			Room room1 = new Room();
			room1.setRoomNumber(101);
			room1.setType(RoomType.SINGLE);
			room1.setQuality(3);
			room1.setIsAvailable(true);
			room1.setHotel(hotel);

			Room room2 = new Room();
			room2.setRoomNumber(102);
			room2.setType(RoomType.DOUBLE);
			room2.setQuality(4);
			room2.setIsAvailable(true);
			room2.setHotel(hotel);

			roomRepository.saveAll(List.of(room1, room2));

			// Services
			Service breakfast = new Service();
			breakfast.setName("Breakfast");
			breakfast.setPrice(10.0);

			Service internet = new Service();
			internet.setName("Internet");
			internet.setPrice(5.0);

			serviceRepository.saveAll(List.of(breakfast, internet));

			// Booking
			Booking booking = new Booking();
			booking.setBookingNumber("BK001");
			booking.setBookingDate(LocalDate.now().toString());
			booking.setStartDate(LocalDate.now().plusDays(1));
			booking.setEndDate(LocalDate.now().plusDays(5));
			booking.setCancelationDate(null);
			booking.setTotalPrice(400.0);
			booking.setTourist(tourist);
			booking.setRoom(room1);
			booking.setServices(List.of(breakfast, internet));
			bookingRepository.save(booking);

			// Payment
			Payment payment = new Payment();
			payment.setPaymentDate(LocalDate.now().toString());
			payment.setAmount(400.0);
			payment.setPaymentType(PaymentType.CREDIT_CARD);
			payment.setBooking(booking);
			paymentRepository.save(payment);
		};
	}

}
