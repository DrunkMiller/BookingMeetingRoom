package com.booking.controllers;

import com.booking.dto.TypeEventDto;
import com.booking.models.TypeEvent;
import com.booking.service.TypeEventService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/types")
public class TypeEventController {
    private final TypeEventService typeEventService;

    public TypeEventController(TypeEventService typeEventService) {
        this.typeEventService = typeEventService;
    }

    @GetMapping(value = "")
    public List<TypeEventDto> getAllEvents() {
        return typeEventService.getAllEvents();
    }

    @GetMapping("/{id}")
    public ResponseEntity<TypeEventDto> getEventById(@PathVariable(value = "id") Long eventId) {
        return ResponseEntity.ok().body(typeEventService.getEventById(eventId));
    }

    @PostMapping("/type")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<TypeEventDto> createEvent(@RequestBody TypeEvent event) {
        return ResponseEntity.status(HttpStatus.CREATED).body(typeEventService.createEvent(event));
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> deleteTypeEvent(@PathVariable(value = "id") Long typeEventId) {
        typeEventService.deleteTypeEventById(typeEventId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<TypeEventDto> updateTypeEvent(@PathVariable(value = "id") Long typeEventId,
                                                        @RequestBody TypeEvent typeEventDetails) {
        return ResponseEntity.ok(typeEventService.updateTypeEvent(typeEventId, typeEventDetails));
    }


}
