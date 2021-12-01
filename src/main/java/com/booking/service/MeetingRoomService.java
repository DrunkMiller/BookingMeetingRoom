package com.booking.service;

import com.booking.advice.MeetingRoomNotBookedException;
import com.booking.advice.ResourceNotFoundException;
import com.booking.dto.MeetingRoomDto;
import com.booking.mapper.Convertor;
import com.booking.models.postgres.MeetingRoom;
import com.booking.models.postgres.TypeEvent;
import com.booking.repositories.postgres.MeetingRoomRepo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MeetingRoomService {
    private final TypeEventService typeEventService;
    private final MeetingRoomRepo meetingRoomRepo;
    private final Convertor convertor;
    private final EntityManager entityManager;

    public MeetingRoomService(TypeEventService typeEventService, MeetingRoomRepo meetingRoomRepo, Convertor typeEventConvertor, EntityManager entityManager) {
        this.typeEventService = typeEventService;
        this.meetingRoomRepo = meetingRoomRepo;
        this.convertor = typeEventConvertor;
        this.entityManager = entityManager;
    }

    public List<MeetingRoomDto> getAllMeetingRoom() {
        List<MeetingRoom> meetingRooms = meetingRoomRepo.findAll();
        return meetingRooms.stream()
                .map(room -> convertor.convertToDto(room, MeetingRoomDto.class))
                .collect(Collectors.toList());
    }

    public MeetingRoomDto getMeetingRoomDtoById(Long meetingRoomId) {
        entityManager.clear();
        return convertor.convertToDto(getMeetingRoomById(meetingRoomId), MeetingRoomDto.class);
    }

    @Transactional
    public MeetingRoomDto createMeetingRoom(MeetingRoom meetingRoom) {
        checkStartBeforeAfter(meetingRoom.getWorkTimeWith(), meetingRoom.getWorkTimeBy());
        meetingRoom = meetingRoomRepo.saveAndFlush(meetingRoom);
        return getMeetingRoomDtoById(meetingRoom.getId());
    }

    @Transactional
    public void deleteMeetingRoomById(Long meetingRoomId) {
        MeetingRoom meetingRoom = getMeetingRoomById(meetingRoomId);
        meetingRoomRepo.delete(meetingRoom);
    }


    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public MeetingRoomDto updateMeetingRoom(Long meetingRoomId, MeetingRoom meetingRoom) {
        MeetingRoom meetingRoomNew = meetingRoomRepo.findById(meetingRoomId)
                .orElseThrow(() -> new ResourceNotFoundException("Meeting room with ID number " + meetingRoomId + " does not exist"));
        checkStartBeforeAfter(meetingRoom.getWorkTimeWith(), meetingRoom.getWorkTimeBy());
        meetingRoomNew.setTitle(meetingRoom.getTitle());
        meetingRoomNew.setDescription(meetingRoom.getDescription());
        meetingRoomNew.setNumberOfSeats(meetingRoom.getNumberOfSeats());
        meetingRoomNew.setInteractiveBoard(meetingRoom.isInteractiveBoard());
        meetingRoomNew.setWorkTimeWith(meetingRoom.getWorkTimeWith());
        meetingRoomNew.setWorkTimeBy(meetingRoom.getWorkTimeBy());
        meetingRoomNew.setWorking(meetingRoom.isWorking());
        meetingRoomNew.setTypeEventSet(typeEventService.getAllTypeEventById(meetingRoom.getTypeEventSet()
                .stream()
                .map(TypeEvent::getId).collect(Collectors.toSet())));
        meetingRoomRepo.saveAndFlush(meetingRoomNew);
        return getMeetingRoomDtoById(meetingRoomNew.getId());

    }

    protected MeetingRoom getMeetingRoomById(Long meetingRoomId) {
        return meetingRoomRepo.findById(meetingRoomId)
                .orElseThrow(() -> new ResourceNotFoundException("Meeting room with ID number " + meetingRoomId + " does not exist"));
    }

    private void checkStartBeforeAfter(LocalTime startTime, LocalTime finishTime) {
        if (startTime.isAfter(finishTime)) {
            throw new MeetingRoomNotBookedException("The time working of meeting room is incorrect");
        }
    }
}
