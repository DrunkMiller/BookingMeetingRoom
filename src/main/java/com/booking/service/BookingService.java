package com.booking.service;

import com.booking.advice.MeetingRoomNotBookedException;
import com.booking.advice.ResourceNotFoundException;
import com.booking.models.Booking;
import com.booking.models.MeetingRoom;
import com.booking.models.TypeEvent;
import com.booking.repositories.BookingRepo;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service

public class BookingService {
    private final BookingRepo bookingRepo;

    public BookingService(BookingRepo bookingRepo) {
        this.bookingRepo = bookingRepo;
    }

    public List<Booking> getBookingsFromPreviousDay() {
        return bookingRepo.findBookingByStartTimeAfterOrderByStartTime(LocalDateTime.now().minusDays(1), PageRequest.of(0, 2));
    }

    public Booking getBookingById(Long bookingId) {
        return bookingRepo.findById(bookingId).orElseThrow(() -> new ResourceNotFoundException("Booking with ID " + bookingId + " doesn't exist."));
    }

    public Booking createBooking(Booking booking) {
        if (allChecks(booking)) {
            bookingRepo.save(booking);
        }
        return booking;
    }

    public Map<String, Boolean> updateBooking(Long bookingId, Booking booking) {
        Booking bookingOld = bookingRepo.findById(bookingId)
                .orElseThrow(() -> new ResourceNotFoundException("Booking with ID number " + bookingId + " does not exist"));
        bookingOld.setTitle(booking.getTitle());
        bookingOld.setEmployee(booking.getEmployee());
        bookingOld.setStartTime(booking.getStartTime());
        bookingOld.setFinishTime(booking.getFinishTime());
        bookingOld.setMeetingRoom(booking.getMeetingRoom());
        bookingOld.setTypeEvent(booking.getTypeEvent());
        if (allChecks(bookingOld)) {
            bookingRepo.save(bookingOld);

        }
        Map<java.lang.String, java.lang.Boolean> map = new HashMap<>();
        map.put("Booking Update Successfully", java.lang.Boolean.TRUE);
        return map;
    }

    public Map<String, Boolean> deleteBookingById(Long bookingId) {
        Booking booking = getBookingById(bookingId);
        bookingRepo.delete(booking);
        Map<java.lang.String, java.lang.Boolean> map = new HashMap<>();
        map.put("Booking Deleted Successfully", java.lang.Boolean.TRUE);
        return map;
    }

    private boolean allChecks(Booking booking) {
        isMeetingRoomWorking(booking.getMeetingRoom());//работает ли комната
        checkTimeIsItWorkingTime(booking.getMeetingRoom(), booking.getStartTime(), booking.getFinishTime()); //рабочее ли время комнаты выбрано
        checkSuitableEvent(booking.getTypeEvent(), booking.getMeetingRoom()); // можно ты проводить выбранное событие в этой выбранной комнате
        checkStartBeforeAfter(booking.getStartTime(), booking.getFinishTime());
        checkRoomIsFreeInSelectedTime(booking.getId(), booking.getStartTime(), booking.getFinishTime());
        return true;
    }

    private void isMeetingRoomWorking(MeetingRoom meetingRoom) {
        if (!meetingRoom.isWorking()) {
            throw new MeetingRoomNotBookedException("Unfortunately, the meeting room is not working at the moment.");
        }
    }

    private void checkTimeIsItWorkingTime(MeetingRoom meetingRoom, LocalDateTime startTime, LocalDateTime finishTime) {
        LocalTime stWork = meetingRoom.getWorkTimeWith();
        LocalTime fnsWork = meetingRoom.getWorkTimeBy();
        LocalDateTime startWork = stWork.atDate(startTime.toLocalDate());
        LocalDateTime finishWork = fnsWork.atDate(startTime.toLocalDate());
        if (!((startTime.isAfter(startWork) || startTime.equals(startWork)) && (finishTime.isAfter(startWork)) &&
                (startTime.isBefore(finishWork)) && (finishTime.isBefore(finishWork) || finishTime.equals(finishWork)))) {
            throw new MeetingRoomNotBookedException("At this time, the meeting room does not work.");
        }
    }

    private void checkSuitableEvent(TypeEvent typeEvent, MeetingRoom meetingRoom) {
        boolean result = meetingRoom.getTypeEventSet().contains(typeEvent);
        if (!result) {
            throw new MeetingRoomNotBookedException("It is not possible to hold a '" + typeEvent + "' event in room " + meetingRoom.getTitle() + ".");
        }
    }

    private void checkRoomIsFreeInSelectedTime(Long bookingId, LocalDateTime startTimeReserve, LocalDateTime finishTimeReserve) {
        LocalDateTime startOfDay = startTimeReserve.toLocalDate().atStartOfDay().minusDays(1);
        LocalDateTime finishOfDay = startTimeReserve.toLocalDate().atStartOfDay().plusDays(1);
        List<Booking> bookingList = bookingRepo.findBookingByStartTimeBetweenOrderByStartTime(startOfDay, finishOfDay);
        bookingList.removeIf((booking) -> booking.getId().equals(bookingId));// удалять бронирование из списка найденных, для работы метода update, так как будет возникать конфликт самим с собой
        for (Booking booking : bookingList) {
            if (startTimeReserve.isBefore(booking.getFinishTime()) && finishTimeReserve.isAfter(booking.getStartTime())) {
                throw new MeetingRoomNotBookedException("It is impossible to book for this time, because there was an " +
                        "overlap with another booking");
            }
        }
    }

    private void checkStartBeforeAfter(LocalDateTime startTime, LocalDateTime finishTime) {
        if (startTime.isAfter(finishTime)) {
            throw new MeetingRoomNotBookedException("The time of booking is incorrect");
        }
    }

}
