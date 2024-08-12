package com.interview.notes.y2024.june.test2.parking;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ParkingService {

    @Autowired
    private BookingRepository bookingRepository;

    public Booking bookParking(BookingRequest bookingRequest) {
        Booking booking = new Booking();
        booking.setCarNumber(bookingRequest.getCarNumber());
        booking.setStartTime(bookingRequest.getStartTime());
        booking.setEndTime(bookingRequest.getEndTime());
        booking.setStatus(BookingStatus.BOOKED);
        booking.setAmountPaid(calculateAmount(bookingRequest.getStartTime(), bookingRequest.getEndTime()));
        return bookingRepository.save(booking);
    }

    public Booking cancelBooking(Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId).orElseThrow(() -> new RuntimeException("Booking not found"));
        booking.setStatus(BookingStatus.CANCELLED);
        return bookingRepository.save(booking);
    }

    public MonthlySummary getMonthlySummary(String carNumber, String month) {
        List<Booking> bookings = bookingRepository.findByCarNumberAndMonth(carNumber, month);
        double totalAmount = bookings.stream().mapToDouble(Booking::getAmountPaid).sum();
        return new MonthlySummary(bookings, totalAmount);
    }

    private double calculateAmount(LocalDateTime startTime, LocalDateTime endTime) {
        long hours = Duration.between(startTime, endTime).toHours();
        if (hours <= 4) {
            return 10.0;
        } else {
            return 25.0;
        }
    }
}
