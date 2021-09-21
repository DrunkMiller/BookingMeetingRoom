package com.booking.controllers;

import com.booking.models.MeetingRoom;
import com.booking.service.MeetingRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/meeting_room")
public class MeetingRoomController {

    @Autowired
    private MeetingRoomService meetingRoomService;

    @GetMapping()
    public List<MeetingRoom> getAllMeetingRoom() {
        return meetingRoomService.getAllMeetingRoom();
    }


    @GetMapping("/{id}")
    public ResponseEntity<MeetingRoom> getMeetingRoomById(@PathVariable(value = "id") Long meetingRoomId) {
        MeetingRoom meetingRoom = meetingRoomService.getMeetingRoomById(meetingRoomId);
        return ResponseEntity.ok().body(meetingRoom);
    }

    @PostMapping()
    public ResponseEntity<MeetingRoom> createMeetingRoom(@RequestBody MeetingRoom meetingRoom) {
        meetingRoomService.createMeetingRoom(meetingRoom);
        return ResponseEntity.ok(meetingRoom);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Map<String, Boolean>> deleteMeetingRoomById(@PathVariable(value = "id") Long meetingRoomId) {
        Map<String, Boolean> map = meetingRoomService.deleteMeetingRoomById(meetingRoomId);
        return ResponseEntity.ok(map);
    }


    @PutMapping("{id}")
    public ResponseEntity<MeetingRoom> updateMeetingRoom(@PathVariable(value = "id") Long meetingRoomId,
                                                         @RequestBody MeetingRoom meetingRoom) {
        MeetingRoom meetingRoomOld = meetingRoomService.updateMeetingRoom(meetingRoomId, meetingRoom);
        return ResponseEntity.ok(meetingRoomOld);
    }


}
