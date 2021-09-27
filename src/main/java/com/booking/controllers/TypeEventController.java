package com.booking.controllers;

import com.booking.models.TypeEvent;
import com.booking.service.TypeEventService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/types")
public class TypeEventController {
    private final TypeEventService typeEventService;

    public TypeEventController(TypeEventService typeEventService) {
        this.typeEventService = typeEventService;
    }

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
        return ResponseEntity.status(HttpStatus.CREATED).body(typeEvent);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Map<String, Boolean>> deleteTypeEvent(@PathVariable(value = "id") Long typeEventId) {
        Map<String, Boolean> mapResult = typeEventService.deleteTypeEvent(typeEventId);
        return ResponseEntity.ok(mapResult);
    }

    @PutMapping("{id}")
    public ResponseEntity<Map<String, Boolean>> updateTypeEvent(@PathVariable(value = "id") Long typeEventId,
                                                     @RequestBody TypeEvent typeEventDetails) {
        Map<String, Boolean> map = typeEventService.updateTypeEvent(typeEventId, typeEventDetails);
        return ResponseEntity.ok(map);
    }


}
