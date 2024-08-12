package com.interview.notes.y2024.june.test2.parking;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByCarNumberAndMonth(String carNumber, String month);
}