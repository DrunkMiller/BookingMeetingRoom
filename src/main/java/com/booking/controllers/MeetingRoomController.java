package com.booking.controllers;

import com.booking.models.MeetingRoom;
import com.booking.service.MeetingRoomService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/meeting_rooms")
public class MeetingRoomController {

    private final MeetingRoomService meetingRoomService;

    public MeetingRoomController(MeetingRoomService meetingRoomService) {
        this.meetingRoomService = meetingRoomService;
    }

    @GetMapping()
    public List<MeetingRoom> getAllMeetingRoom() {
        return meetingRoomService.getAllMeetingRoom();
    }


    @GetMapping("/{id}")
    public ResponseEntity<MeetingRoom> getMeetingRoomById(@PathVariable(value = "id") Long meetingRoomId) {
        MeetingRoom meetingRoom = meetingRoomService.getMeetingRoomById(meetingRoomId);
        return ResponseEntity.ok().body(meetingRoom);
    }

    @Secured("ROLE_ADMIN")
    @PostMapping("/meet_room")
    public ResponseEntity<MeetingRoom> createMeetingRoom(@RequestBody MeetingRoom meetingRoom) {
        meetingRoomService.createMeetingRoom(meetingRoom);
        return ResponseEntity.status(HttpStatus.CREATED).body(meetingRoom);
    }

    @Secured("ROLE_ADMIN")
    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteMeetingRoomById(@PathVariable(value = "id") Long meetingRoomId) {
        meetingRoomService.deleteMeetingRoomById(meetingRoomId);
        return ResponseEntity.ok().build();
    }

    @Secured("ROLE_ADMIN")
    @PutMapping("{id}")
    public ResponseEntity<MeetingRoom> updateMeetingRoom(@PathVariable(value = "id") Long meetingRoomId,
                                                         @RequestBody MeetingRoom meetingRoom) {
        MeetingRoom updateRoom = meetingRoomService.updateMeetingRoom(meetingRoomId, meetingRoom);
        return ResponseEntity.ok().body(updateRoom);
    }


}
