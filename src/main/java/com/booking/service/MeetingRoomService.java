package com.booking.service;

import com.booking.advice.ResourceNotFoundException;
import com.booking.models.MeetingRoom;
import com.booking.models.TypeEvent;
import com.booking.repositories.MeetingRoomRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

@Service
public class MeetingRoomService {

    @Autowired
    private MeetingRoomRepo meetingRoomRepo;

    public List<MeetingRoom> getAllMeetingRoom(){
        return meetingRoomRepo.findAll();
    }

    public MeetingRoom getMeetingRoomById(Long meetingRoomId){
        return meetingRoomRepo.findById(meetingRoomId)
                .orElseThrow(()->new ResourceNotFoundException("Meeting room with ID number "+ meetingRoomId + " does not exist"));
    }

    public MeetingRoom createMeetingRoom(MeetingRoom meetingRoom) {
        meetingRoomRepo.save(meetingRoom);
        return meetingRoom;
    }

    public Map<String, Boolean> deleteMeetingRoomById(Long meetingRoomId){
        MeetingRoom meetingRoom = getMeetingRoomById(meetingRoomId);
        meetingRoomRepo.delete(meetingRoom);
        Map<String, Boolean> map = new HashMap<>();
        map.put("Meeting Room Deleted Successfully", Boolean.TRUE);
        return map;
    }


    public MeetingRoom updateMeetingRoom(Long meetingRoomId, MeetingRoom meetingRoom){
        MeetingRoom meetingRoomOld = meetingRoomRepo.findById(meetingRoomId)
                .orElseThrow(()->new ResourceNotFoundException("Meeting room with ID number "+ meetingRoomId + " does not exist"));
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
}
