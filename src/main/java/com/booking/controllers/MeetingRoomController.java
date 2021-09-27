package com.booking.controllers;

import com.booking.models.MeetingRoom;
import com.booking.service.MeetingRoomService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Map;

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

    @PostMapping("/meet_room")
    public ResponseEntity<MeetingRoom> createMeetingRoom(@RequestBody MeetingRoom meetingRoom) {
        meetingRoomService.createMeetingRoom(meetingRoom);
    return ResponseEntity.status(HttpStatus.CREATED).body(meetingRoom);
    }


    @DeleteMapping("{id}")
    public ResponseEntity<Map<String, Boolean>> deleteMeetingRoomById(@PathVariable(value = "id") Long meetingRoomId) {
        Map<String, Boolean> map = meetingRoomService.deleteMeetingRoomById(meetingRoomId);
        return ResponseEntity.ok(map);
    }


    @PutMapping("{id}")
    public ResponseEntity<Map<String, Boolean>> updateMeetingRoom(@PathVariable(value = "id") Long meetingRoomId,
                                                         @RequestBody MeetingRoom meetingRoom) {
        Map<String, Boolean> map = meetingRoomService.updateMeetingRoom(meetingRoomId, meetingRoom);
        return ResponseEntity.ok(map);
    }


}
