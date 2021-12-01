package com.booking.repositories.postgres.bookingrepocustom;

import com.booking.models.postgres.Booking;

import java.util.List;

public interface BookingRepositoriesCustom {
    List<Booking> getBookingSelectedUser(Long userId);
}
