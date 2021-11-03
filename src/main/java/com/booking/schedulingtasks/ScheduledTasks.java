package com.booking.schedulingtasks;

import com.booking.models.Booking;
import com.booking.models.MeetingRoom;
import com.booking.repositories.BookingRepo;
import com.booking.repositories.MeetingRoomRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Component
@Slf4j
public class ScheduledTasks {

    private final BookingRepo bookingRepo;
    private final MeetingRoomRepo meetingRoomRepo;

    public ScheduledTasks(BookingRepo bookingRepo, MeetingRoomRepo meetingRoomRepo) {
        this.bookingRepo = bookingRepo;
        this.meetingRoomRepo = meetingRoomRepo;
    }

    @Scheduled(fixedRateString = "${fixedDelay.in.milliseconds}", initialDelayString ="${initialDelay.in.milliseconds}" )
    public void reportNumberOfBooking() {

        try {
            LocalDateTime nowTime = LocalDateTime.now();
            List<Booking> bookingList =bookingRepo.findBookingByBookingTimeBetween(nowTime.minusHours(1), nowTime);
            List<MeetingRoom> meetingRoomList = meetingRoomRepo.findAll();
            Map<MeetingRoom , Long> roomIntegerMap = new HashMap<>();
            for (MeetingRoom meetingRoom: meetingRoomList){
                roomIntegerMap.put(meetingRoom, bookingList.stream().filter(booking -> booking
                        .getMeetingRoom().getId().equals(meetingRoom.getId()))
                        .count());
            }
            log.info("=====================");
            log.info("Number of new bookings in the last hour - " + bookingList.size());
            for (MeetingRoom meetingRoom:roomIntegerMap.keySet()){
                log.info("Meeting room '" + meetingRoom.getTitle() + "' bookings in the past hour equals " + roomIntegerMap.get(meetingRoom));
            }
        }
        catch (NullPointerException e){
            log.info("There were no reservations for the last hour.");
        }
        finally {
            log.info("==============================");
        }
    }

}
