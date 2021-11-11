package com.booking.unit.service;

import com.booking.advice.EntityAlreadyExistException;
import com.booking.advice.ResourceNotFoundException;
import com.booking.dto.TypeEventDto;
import com.booking.mapper.Convertor;
import com.booking.models.TypeEvent;
import com.booking.repositories.TypeEventRepo;
import com.booking.service.TypeEventService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class TypeEventServiceUnitTest {

    @InjectMocks
    private TypeEventService typeEventService;
    @Mock
    private TypeEventRepo typeEventRepo;
    @Mock
    private Convertor convertor;
    private TypeEventDto typeEventDto1;
    private TypeEventDto typeEventDto2;
    private TypeEvent typeEvent1;
    private TypeEvent typeEvent2;
    private List<TypeEventDto> eventDtoList;
    private List<TypeEvent> eventList;
    private final String type1 = "testEvent1";
    private final String type2 = "testEvent2";

    @BeforeEach
    public void setUp() {
        typeEvent1 = new TypeEvent();
        typeEvent1.setType(type1);
        typeEvent1.setId(1L);
        typeEvent2 = new TypeEvent();
        typeEvent2.setType(type2);

        typeEventDto1 = new TypeEventDto();
        typeEventDto1.setType(type1);
        typeEventDto2 = new TypeEventDto();
        typeEventDto2.setType(type2);
        eventDtoList = new ArrayList<>(Arrays.asList(typeEventDto1, typeEventDto2));
        eventList = new ArrayList<>(Arrays.asList(typeEvent1, typeEvent2));

    }


    @AfterEach
    public void tearDown() {
        typeEvent1 = typeEvent2 = null;
        eventDtoList = null;
        eventList = null;

    }

    @Test
    public void getEventByIdTest() {
        when(typeEventRepo.findById(any())).thenReturn(Optional.of(typeEvent1));
        when(convertor.convertToDto(typeEvent1, TypeEventDto.class)).thenReturn(typeEventDto1);
        assertEquals(typeEventService.getEventById(typeEvent1.getId()), typeEventDto1);
        verify(typeEventRepo, times(1)).findById(any());
    }

    @Test
    public void getEventByIdWhichNotExistTest() {
        Exception exception = assertThrows(ResourceNotFoundException.class, () -> typeEventService.getEventById(typeEvent1.getId()));
        assertEquals(exception.getMessage(), "Type event with ID number '" + typeEvent1.getId() + "' does not exist");
        verify(typeEventRepo, times(1)).findById(any());
    }

    @Test
    public void getAllEventsTest() {
        when(convertor.convertToDto(typeEvent1, TypeEventDto.class)).thenReturn(typeEventDto1);
        when(convertor.convertToDto(typeEvent2, TypeEventDto.class)).thenReturn(typeEventDto2);
        when(typeEventRepo.findAll()).thenReturn(eventList);
        assertEquals(typeEventService.getAllEvents(), eventDtoList);
        verify(typeEventRepo, times(1)).findAll();
    }

    @Test
    public void createEventTest() {
        when(typeEventRepo.save(any())).thenReturn(typeEvent1);
        when(convertor.convertToDto(typeEvent1, TypeEventDto.class)).thenReturn(typeEventDto1);
        assertEquals(typeEventService.createEvent(typeEvent1), typeEventDto1);
        verify(typeEventRepo, times(1)).save(any());
    }

    @Test
    public void createEventThrowsAlreadyExistExceptionTest() {
        when(typeEventRepo.findByType(any())).thenReturn(typeEvent1);
        assertThrows(EntityAlreadyExistException.class, () -> typeEventService.createEvent(typeEvent1));
        verify(typeEventRepo, times(1)).findByType(any());
    }


    @Test
    public void updateTypeEventTest() {
        when(typeEventRepo.findByType(any())).thenReturn(null);
        when(typeEventRepo.findById(typeEvent1.getId())).thenReturn(java.util.Optional.ofNullable(typeEvent1));
        when(convertor.convertToDto(typeEvent1, TypeEventDto.class)).thenReturn(typeEventDto1);
        assertEquals(typeEventService.updateTypeEvent(typeEvent1.getId(), typeEvent2), typeEventDto1);
        verify(typeEventRepo, times(1)).save(any());
        verify(typeEventRepo, times(1)).findByType(any());
    }

    @Test
    public void updateTypeEventThrowsAlreadyExistTest() {
        when(typeEventRepo.findByType(any())).thenReturn(typeEvent1);
        when(typeEventRepo.findById(typeEvent1.getId())).thenReturn(java.util.Optional.ofNullable(typeEvent1));
        assertThrows(EntityAlreadyExistException.class, () -> typeEventService.updateTypeEvent(typeEvent1.getId(), typeEvent2));
        verify(typeEventRepo, times(0)).save(any());
        verify(typeEventRepo, times(1)).findByType(any());
    }


}