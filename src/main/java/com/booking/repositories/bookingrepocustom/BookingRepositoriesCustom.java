package com.booking.repositories.bookingrepocustom;

import com.booking.models.Booking;

import java.util.List;

public interface BookingRepositoriesCustom {
    List<Booking> getBookingSelectedUser(Long userId);
}
