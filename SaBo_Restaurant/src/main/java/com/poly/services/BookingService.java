package com.poly.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.poly.entities.Booking;
import com.poly.repositories.BookingRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookingService {
	private final BookingRepository bookingRepository;
	
	public List<Booking> historyByCustomerUsername(String username) {
		return bookingRepository.findByUserAccountUsername(username);
	}
	
	public List<Booking> findByRestaurantid(int id) {
		return bookingRepository.findByRestaurantId(id);
	}
	
	public Booking checkout(Booking booking) {
		//Booking check = bookingRepository.findByCreatedDateAndBookingDate(booking.getCreatedDate(), booking.getBookingDate());
		//if(check == null) return bookingRepository.save(booking);
		//return check;
		return bookingRepository.save(booking);	
	}
}
