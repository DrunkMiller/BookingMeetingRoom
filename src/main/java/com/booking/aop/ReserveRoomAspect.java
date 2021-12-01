package com.booking.aop;

import com.booking.dto.BookingDto;
import com.booking.models.mongodb.MeetingRoomReservation;
import com.booking.repositories.mongodb.RoomReservationRepo;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Slf4j
@Aspect
@Component
public class ReserveRoomAspect {
    private final RoomReservationRepo roomReservationRepo;

    public ReserveRoomAspect(RoomReservationRepo roomReservationRepo) {
        this.roomReservationRepo = roomReservationRepo;
    }

    @Pointcut("@annotation(WriteBookingInMongo)")
    public void statisticsCreateBooking() {
    }

    @AfterReturning(value = "statisticsCreateBooking()", returning = "bookingDto")
    public void writingInDbSuccessfulBooking(JoinPoint jp, BookingDto bookingDto) {
        roomReservationRepo.insert(createdRoomReserve(bookingDto));
    }

    @AfterThrowing(pointcut = "statisticsCreateBooking()",
            throwing = "ex")
    public void writingInDbUnsuccessfulBooking(RuntimeException ex) {
        log.info("An exception was thrown: \"" + ex.getMessage()+"\"");
    }

    private MeetingRoomReservation createdRoomReserve(BookingDto bookingDto) {
        MeetingRoomReservation roomReservation = new MeetingRoomReservation();
        roomReservation.setDateCreated(LocalDateTime.now());
        roomReservation.setMeetingRoomDto(bookingDto.getMeetingRoom());
        roomReservation.setSuccessfulReserve(true);
        return roomReservation;
    }
}
