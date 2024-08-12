package com.interview.notes.y2024.june.test2.parking;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/parking")
public class ParkingController {

    @Autowired
    private ParkingService parkingService;

    @PostMapping("/book")
    public ResponseEntity<Booking> bookParking(@RequestBody BookingRequest bookingRequest) {
        Booking booking = parkingService.bookParking(bookingRequest);
        return new ResponseEntity<>(booking, HttpStatus.CREATED);
    }

    @PutMapping("/cancel/{bookingId}")
    public ResponseEntity<Booking> cancelBooking(@PathVariable Long bookingId) {
        Booking booking = parkingService.cancelBooking(bookingId);
        return new ResponseEntity<>(booking, HttpStatus.OK);
    }

    @GetMapping("/summary/{carNumber}/{month}")
    public ResponseEntity<MonthlySummary> getMonthlySummary(@PathVariable String carNumber, @PathVariable String month) {
        MonthlySummary summary = parkingService.getMonthlySummary(carNumber, month);
        return new ResponseEntity<>(summary, HttpStatus.OK);
    }
}
