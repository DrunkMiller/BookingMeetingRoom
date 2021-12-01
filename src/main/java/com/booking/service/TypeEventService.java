package com.booking.service;

import com.booking.advice.EntityAlreadyExistException;
import com.booking.advice.ResourceNotFoundException;
import com.booking.dto.TypeEventDto;
import com.booking.mapper.Convertor;
import com.booking.models.postgres.TypeEvent;
import com.booking.repositories.postgres.TypeEventRepo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TypeEventService {
    private final TypeEventRepo typeEventRepo;
    private final Convertor convertor;

    public TypeEventService(TypeEventRepo typeEventRepo, Convertor convertor) {
        this.typeEventRepo = typeEventRepo;
        this.convertor = convertor;
    }

    public List<TypeEventDto> getAllEvents() {
        List<TypeEvent> typeEventList = typeEventRepo.findAll();
        return typeEventList.stream().map(event -> convertor.convertToDto(event, TypeEventDto.class))
                .collect(Collectors.toList());
    }

    public TypeEventDto getEventById(Long eventId) {
        return convertor.convertToDto(getByID(eventId), TypeEventDto.class);
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public TypeEventDto createEvent(TypeEvent event) {
        TypeEvent typeEvent = typeEventRepo.findByType(event.getType());
        if (typeEvent == null) {
            return convertor.convertToDto(typeEventRepo.save(event), TypeEventDto.class);
        } else throw new EntityAlreadyExistException("Event type '" + event.getType() + "' already exists");
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public void deleteTypeEventById(Long typeEventId) {
        TypeEvent typeEvent = getByID(typeEventId);
        typeEventRepo.delete(typeEvent);
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public TypeEventDto updateTypeEvent(Long typeEventId, TypeEvent typeEventDetails) {
        TypeEvent typeEvent = getByID(typeEventId);
        TypeEvent typeEventDB = typeEventRepo.findByType(typeEventDetails.getType());
        if (typeEventDB == null) {
            typeEvent.setType(typeEventDetails.getType());
            typeEventRepo.save(typeEvent);
        } else throw new EntityAlreadyExistException("Event type '" + typeEventDetails.getType() + "' already exists");

        return convertor.convertToDto(typeEvent, TypeEventDto.class);
    }

    protected Set<TypeEvent> getAllTypeEventById(Set<Long> listId) {
        List<TypeEvent> typeEventList = typeEventRepo.findAllById(listId);
        return new HashSet<>(typeEventList);
    }

    protected TypeEvent getByID(Long eventId) {
        return typeEventRepo.findById(eventId)
                .orElseThrow(() -> new ResourceNotFoundException("Type event with ID number '" + eventId + "' does not exist"));
    }


}
