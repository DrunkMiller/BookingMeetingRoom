package com.booking.controllers;
import com.booking.models.Booking;
import com.booking.repositories.BookingRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class BookingController {
    @Autowired
    private BookingRepo bookingRepo;

    @GetMapping("/bookings")
    public List<Booking> viewBooking(){
        return bookingRepo.findAll();
    }




}
