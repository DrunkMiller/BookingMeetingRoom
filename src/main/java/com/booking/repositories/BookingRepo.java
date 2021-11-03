package com.booking.repositories;

import com.booking.models.Booking;
import com.booking.repositories.bookingrepocustom.BookingRepositoriesCustom;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface BookingRepo extends JpaRepository<Booking, Long>, BookingRepositoriesCustom {
    List<Booking> findBookingByStartTimeAfterOrderByStartTime(LocalDateTime startTime, Pageable pageable);

    List<Booking> findBookingByBookingTimeBetween(LocalDateTime startDay, LocalDateTime finishDay);

    List<Booking> findBookingByStartTimeBetweenOrderByStartTime(LocalDateTime startDay, LocalDateTime finishDay);
}
