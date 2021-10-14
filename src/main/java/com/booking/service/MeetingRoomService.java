package com.booking.service;

import com.booking.advice.MeetingRoomNotBookedException;
import com.booking.advice.ResourceNotFoundException;
import com.booking.models.MeetingRoom;
import com.booking.repositories.MeetingRoomRepo;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;

@Service
public class MeetingRoomService {

    private final MeetingRoomRepo meetingRoomRepo;

    public MeetingRoomService(MeetingRoomRepo meetingRoomRepo) {
        this.meetingRoomRepo = meetingRoomRepo;
    }

    public List<MeetingRoom> getAllMeetingRoom() {
        return meetingRoomRepo.findAll();
    }

    public MeetingRoom getMeetingRoomById(Long meetingRoomId) {
        return meetingRoomRepo.findById(meetingRoomId)
                .orElseThrow(() -> new ResourceNotFoundException("Meeting room with ID number " + meetingRoomId + " does not exist"));
    }

    public void createMeetingRoom(MeetingRoom meetingRoom) {
        checkStartBeforeAfter(meetingRoom.getWorkTimeWith(), meetingRoom.getWorkTimeBy());
        meetingRoomRepo.save(meetingRoom);
    }

    public void deleteMeetingRoomById(Long meetingRoomId) {
        MeetingRoom meetingRoom = getMeetingRoomById(meetingRoomId);
        meetingRoomRepo.delete(meetingRoom);

    }


    public MeetingRoom updateMeetingRoom(Long meetingRoomId, MeetingRoom meetingRoom) {
        MeetingRoom meetingRoomOld = meetingRoomRepo.findById(meetingRoomId)
                .orElseThrow(() -> new ResourceNotFoundException("Meeting room with ID number " + meetingRoomId + " does not exist"));
        checkStartBeforeAfter(meetingRoom.getWorkTimeWith(), meetingRoom.getWorkTimeBy());
        meetingRoomOld.setTitle(meetingRoom.getTitle());
        meetingRoomOld.setDescription(meetingRoom.getDescription());
        meetingRoomOld.setNumberOfSeats(meetingRoom.getNumberOfSeats());
        meetingRoomOld.setInteractiveBoard(meetingRoom.isInteractiveBoard());
        meetingRoomOld.setWorkTimeWith(meetingRoom.getWorkTimeWith());
        meetingRoomOld.setWorkTimeBy(meetingRoom.getWorkTimeBy());
        meetingRoomOld.setWorking(meetingRoom.isWorking());
        meetingRoomOld.setTypeEventSet(meetingRoom.getTypeEventSet());
        meetingRoomRepo.save(meetingRoomOld);
        return meetingRoomOld;

    }

    private void checkStartBeforeAfter(LocalTime startTime, LocalTime finishTime) {
        if (startTime.isAfter(finishTime)) {
            throw new MeetingRoomNotBookedException("The time working of meeting room is incorrect");
        }
    }
}
