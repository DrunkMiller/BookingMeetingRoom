package com.booking.service;

import com.booking.advice.EntityAlreadyExistException;
import com.booking.advice.ResourceNotFoundException;
import com.booking.models.TypeEvent;
import com.booking.repositories.TypeEventRepo;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TypeEventService {
    private final TypeEventRepo typeEventRepo;

    public TypeEventService(TypeEventRepo typeEventRepo) {
        this.typeEventRepo = typeEventRepo;
    }

    public List<TypeEvent> getAllEvents() {
        return typeEventRepo.findAll();
    }

    public TypeEvent getEventById(Long eventId) {
        return typeEventRepo.findById(eventId)
                .orElseThrow(() -> new ResourceNotFoundException("Type event with ID number '" + eventId + "' does not exist"));
    }

    public TypeEvent createEvent(TypeEvent event) {
        TypeEvent typeEvent = typeEventRepo.findByType(event.getType());
        if (typeEvent == null) {
            typeEventRepo.save(event);
        } else throw new EntityAlreadyExistException("Event type '" + event.getType() + "' already exists");
        return event;
    }

    public Map<String, Boolean> deleteTypeEvent(Long typeEventId) {
        TypeEvent typeEvent = getEventById(typeEventId);
        typeEventRepo.delete(typeEvent);
        Map<String, Boolean> map = new HashMap<>();
        map.put("Event Deleted Successfully", Boolean.TRUE);
        return map;
    }

    public TypeEvent updateTypeEvent(Long typeEventId, TypeEvent typeEventDetails) {
        TypeEvent typeEvent = getEventById(typeEventId);
        TypeEvent typeEventDB = typeEventRepo.findByType(typeEventDetails.getType());
        if (typeEventDB == null) {
            typeEvent.setType(typeEventDetails.getType());
            typeEventRepo.save(typeEvent);
        } else throw new EntityAlreadyExistException("Event type '" + typeEventDetails.getType() + "' already exists");
        return typeEvent;
    }


}
