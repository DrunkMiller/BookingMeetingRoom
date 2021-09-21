package com.booking.controllers;

import com.booking.models.TypeEvent;
import com.booking.service.TypeEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/types")
public class TypeEventController {
    @Autowired
    private TypeEventService typeEventService;

    @GetMapping(value = "")
    public List<TypeEvent> getAllEvents() {
        return typeEventService.getAllEvents();
    }

    @GetMapping("/{id}")
    public ResponseEntity<TypeEvent> getEventById(@PathVariable(value = "id") Long eventId) {
        return ResponseEntity.ok().body(typeEventService.getEventById(eventId));
    }

    @PostMapping("/type")
    public ResponseEntity<TypeEvent> createEvent(@RequestBody TypeEvent event) {
        TypeEvent typeEvent = typeEventService.createEvent(event);
        return ResponseEntity.ok().body(typeEvent);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Map<String, Boolean>> deleteTypeEvent(@PathVariable(value = "id") Long typeEventId) {
        Map<String, Boolean> mapResult = typeEventService.deleteTypeEvent(typeEventId);
        return ResponseEntity.ok(mapResult);
    }

    @PutMapping("{id}")
    public ResponseEntity<TypeEvent> updateTypeEvent(@PathVariable(value = "id") Long typeEventId,
                                                     @RequestBody TypeEvent typeEventDetails) {
        TypeEvent typeEvent = typeEventService.updateTypeEvent(typeEventId, typeEventDetails);
        return ResponseEntity.ok(typeEvent);
    }


}
