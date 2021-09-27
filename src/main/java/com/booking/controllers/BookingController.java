package com.booking.controllers;

import com.booking.models.Booking;
import com.booking.service.BookingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/bookings")
public class BookingController {
    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @GetMapping("")
    public List<Booking> viewBooking() {
        return bookingService.getBookingsFromPreviousDay();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Booking> getBookingById(@PathVariable(value = "id") Long bookingId) {
        return ResponseEntity.ok().body(bookingService.getBookingById(bookingId));
    }

    @PostMapping("/booking")
    public ResponseEntity<Booking> createBooking(@RequestBody Booking booking) {
        return ResponseEntity.status(HttpStatus.CREATED).body(bookingService.createBooking(booking));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Boolean>> updateBooking(@PathVariable(value = "id") Long bookingId, @RequestBody Booking booking) {
        return ResponseEntity.ok(bookingService.updateBooking(bookingId, booking));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Boolean>> deleteBooking(@PathVariable(value = "id") Long bookingId) {
        Map<String, Boolean> map = bookingService.deleteBookingById(bookingId);
        return ResponseEntity.ok(map);
    }


}
