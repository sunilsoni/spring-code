package com.interview.notes.y2024.june.test2.parking;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.Data;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String carNumber;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Double amountPaid;
    private BookingStatus status;

    // Getters and Setters
}


