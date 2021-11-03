package com.booking.controllers;

import com.booking.dto.BookingDto;
import com.booking.models.Booking;
import com.booking.service.BookingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bookings")
@Slf4j
public class BookingController {
    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @GetMapping("")
    public List<BookingDto> viewBooking() {
        return bookingService.getBookingsFromPreviousDay();
    }

    @GetMapping("my_booking")
    public List<BookingDto> viewBookingAuthenticationUser(Authentication authentication) {
        return bookingService.getBookingAuthenticationUser(authentication);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookingDto> getBookingById(@PathVariable(value = "id") Long bookingId) {
        return ResponseEntity.ok().body(bookingService.getBookingDtoById(bookingId));
    }

    @Secured({"ROLE_ADMIN", "ROLE_LECTOR"})
    @PostMapping("/booking")
    public ResponseEntity<BookingDto> createBooking(@RequestBody Booking booking, Authentication authentication) {
        BookingDto newBookingDto = bookingService.createBooking(booking, authentication);
        log.info("Booking was created in a {} room on {}", booking.getMeetingRoom().getTitle(), booking.getStartTime().toLocalDate());
        return ResponseEntity.status(HttpStatus.CREATED).body(newBookingDto);
    }

    @Secured({"ROLE_ADMIN", "ROLE_LECTOR"})
    @PutMapping("/{id}")
    public ResponseEntity<BookingDto> updateBooking(@PathVariable(value = "id") Long bookingId, @RequestBody Booking booking, Authentication authentication) {
        BookingDto newBookingDto = bookingService.updateBooking(bookingId, booking, authentication);
        log.info("Booking was updated in a {} room on {}", booking.getMeetingRoom().getTitle(), booking.getStartTime().toLocalDate());
        return ResponseEntity.ok().body(newBookingDto);
    }

    @Secured({"ROLE_ADMIN", "ROLE_LECTOR"})
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBooking(@PathVariable(value = "id") Long bookingId, Authentication authentication) {
        bookingService.deleteBookingById(bookingId, authentication);
        log.info("Booking with {} has been deleted.", bookingId);
        return ResponseEntity.ok().build();
    }


}
