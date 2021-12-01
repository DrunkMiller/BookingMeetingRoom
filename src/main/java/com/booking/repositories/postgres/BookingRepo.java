package com.booking.repositories.postgres;

import com.booking.models.postgres.Booking;
import com.booking.repositories.postgres.bookingrepocustom.BookingRepositoriesCustom;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface BookingRepo extends JpaRepository<Booking, Long>, BookingRepositoriesCustom {
    List<Booking> findBookingByStartTimeAfterOrderByStartTime(LocalDateTime startTime, Pageable pageable);

    List<Booking> findBookingByBookingTimeBetween(LocalDateTime startDay, LocalDateTime finishDay);

    List<Booking> findBookingByStartTimeBetweenAndMeetingRoomIdOrderByStartTime(LocalDateTime startDay, LocalDateTime finishDay, Long meetRoomId);
}
