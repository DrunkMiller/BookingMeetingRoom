package com.booking.repositories;

import com.booking.models.Booking;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
//делаем решение чтобы его удаление происходила апдейтом в базе
public interface BookingRepo extends JpaRepository<Booking, Long> {
    List<Booking> findBookingByStartTimeAfterOrderByStartTime(LocalDateTime startTime, Pageable pageable);

    List<Booking> findBookingByStartTimeBetweenOrderByStartTime(LocalDateTime startDay, LocalDateTime finishDay);
}
