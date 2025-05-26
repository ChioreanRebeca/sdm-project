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

			// Hotel 1
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
			room1.setPrice(100.0);

			Room room2 = new Room();
			room2.setRoomNumber(102);
			room2.setType(RoomType.DOUBLE);
			room2.setQuality(4);
			room2.setIsAvailable(true);
			room2.setHotel(hotel);
			room2.setPrice(150.0);

			roomRepository.saveAll(List.of(room1, room2));


			// === Hotel 2 ===
			Hotel hotel2 = new Hotel("Mountain View Resort", "Hilltop Rd 23", 5.0, 80);
			hotelRepository.save(hotel2);

			Room room3 = new Room(201, RoomType.SINGLE, 5, 250.0, true, hotel2);
			Room room4 = new Room(202, RoomType.DOUBLE, 4, 180.0, true, hotel2);
			roomRepository.saveAll(List.of(room3, room4));

			// === Hotel 3 ===
			Hotel hotel3 = new Hotel("City Central Inn", "Downtown Ave 9", 3.0, 60);
			hotelRepository.save(hotel3);

			Room room5 = new Room(301, RoomType.SINGLE, 2, 80.0, true, hotel3);
			Room room6 = new Room(302, RoomType.DOUBLE, 3, 120.0, true, hotel3);
			roomRepository.saveAll(List.of(room5, room6));

			// === Hotel 4 ===
			Hotel hotel4 = new Hotel("Lakeside Retreat", "Lakeview St 77", 4.5, 70);
			hotelRepository.save(hotel4);

			Room room7 = new Room(401, RoomType.SINGLE, 5, 300.0, true, hotel4);
			Room room8 = new Room(402, RoomType.DOUBLE, 4, 170.0, true, hotel4);
			roomRepository.saveAll(List.of(room7, room8));



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
			booking.setBookingDate(LocalDate.now());
			booking.setCheckInDate(LocalDate.now().plusDays(1));
			booking.setCheckOutDate(LocalDate.now().plusDays(5));
			booking.setCancelationDate(null);
			booking.setTotalPrice(400.0);
			booking.setTourist(tourist);
			booking.setRoom(room1);
			booking.setServices(List.of(breakfast, internet));
			bookingRepository.save(booking);

			/*// Payment
			Payment payment = new Payment();
			payment.setPaymentDate(LocalDate.now().toString());
			payment.setAmount(400.0);
			payment.setPaymentType(PaymentType.CREDIT_CARD);
			payment.setBooking(booking);
			paymentRepository.save(payment);*/
		};
	}

}
