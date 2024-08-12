package com.interview.notes.y2024.june.test2.parking;

import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@SpringBootTest
public class ParkingServiceTests {

    @Autowired
    private ParkingService parkingService;

    @MockBean
    private BookingRepository bookingRepository;

    @Test
    public void testBookParking() {
        BookingRequest request = new BookingRequest("ABC123", LocalDateTime.now(), LocalDateTime.now().plusHours(3));
        Booking booking = parkingService.bookParking(request);
        assertEquals("ABC123", booking.getCarNumber());
        assertEquals(BookingStatus.BOOKED, booking.getStatus());
        assertEquals(10.0, booking.getAmountPaid());
    }

    @Test
    public void testCancelBooking() {
        Booking booking = new Booking();
        booking.setId(1L);
        booking.setStatus(BookingStatus.BOOKED);
        when(bookingRepository.findById(1L)).thenReturn(Optional.of(booking));

        Booking cancelledBooking = parkingService.cancelBooking(1L);
        assertEquals(BookingStatus.CANCELLED, cancelledBooking.getStatus());
    }

    @Test
    public void testGetMonthlySummary() {
        List<Booking> bookings = Arrays.asList(new Booking(1L, "ABC123", LocalDateTime.now(), LocalDateTime.now().plusHours(3), 10.0, BookingStatus.BOOKED));
        when(bookingRepository.findByCarNumberAndMonth("ABC123", "2024-07")).thenReturn(bookings);

        MonthlySummary summary = parkingService.getMonthlySummary("ABC123", "2024-07");
        assertEquals(1, summary.getBookings().size());
        assertEquals(10.0, summary.getTotalAmount());
    }
}
