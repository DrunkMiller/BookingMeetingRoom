package com.booking.controllers;
import com.booking.models.Booking;
import com.booking.models.Employee;
import com.booking.repositories.BookingRepo;
import com.booking.repositories.EmployeeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class BookingController {
    @Autowired
    private BookingRepo bookingRepo;

    @GetMapping("/bookings")
    public List<Booking> viewBooking(){
        return bookingRepo.findAll();
    }




}
