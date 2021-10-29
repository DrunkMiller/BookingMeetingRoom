package com.booking.service;

import com.booking.advice.MeetingRoomNotBookedException;
import com.booking.advice.ResourceNotFoundException;
import com.booking.dto.MeetingRoomDto;
import com.booking.dto.TypeEventDto;
import com.booking.mapper.Convertor;
import com.booking.models.MeetingRoom;
import com.booking.models.TypeEvent;
import com.booking.repositories.MeetingRoomRepo;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MeetingRoomService {

    private final MeetingRoomRepo meetingRoomRepo;
    private final Convertor convertor;

    public MeetingRoomService(MeetingRoomRepo meetingRoomRepo , Convertor typeEventConvertor) {
        this.meetingRoomRepo = meetingRoomRepo;
        this.convertor = typeEventConvertor;
    }

    public List<MeetingRoomDto> getAllMeetingRoom() {
        List<MeetingRoom> meetingRooms = meetingRoomRepo.findAll();
        return meetingRooms.stream()
                .map(room -> convertor.convertToDto(room, MeetingRoomDto.class))
                .collect(Collectors.toList());
    }

    public MeetingRoomDto getMeetingRoomDtoById(Long meetingRoomId) {
        return convertor.convertToDto(getMeetingRoomById(meetingRoomId), MeetingRoomDto.class);
    }

    public MeetingRoomDto createMeetingRoom(MeetingRoom meetingRoom) {
        checkStartBeforeAfter(meetingRoom.getWorkTimeWith(), meetingRoom.getWorkTimeBy());
        meetingRoomRepo.save(meetingRoom);
        return convertor.convertToDto(meetingRoom, MeetingRoomDto.class);
    }

    public void deleteMeetingRoomById(Long meetingRoomId) {
        MeetingRoom meetingRoom = getMeetingRoomById(meetingRoomId);
        meetingRoomRepo.delete(meetingRoom);
    }


    public MeetingRoomDto updateMeetingRoom(Long meetingRoomId, MeetingRoom meetingRoom) {
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
        return convertor.convertToDto(meetingRoomOld, MeetingRoomDto.class);

    }

    private MeetingRoom getMeetingRoomById(Long meetingRoomId) {
        return meetingRoomRepo.findById(meetingRoomId)
                .orElseThrow(() -> new ResourceNotFoundException("Meeting room with ID number " + meetingRoomId + " does not exist"));
    }

    private void checkStartBeforeAfter(LocalTime startTime, LocalTime finishTime) {
        if (startTime.isAfter(finishTime)) {
            throw new MeetingRoomNotBookedException("The time working of meeting room is incorrect");
        }
    }
}
