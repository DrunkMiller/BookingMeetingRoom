//package com.booking.unit.service;
//
//import com.booking.advice.EntityAlreadyExistException;
//import com.booking.advice.ResourceNotFoundException;
//import com.booking.models.TypeEvent;
//import com.booking.repositories.TypeEventRepo;
//import com.booking.service.TypeEventService;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertThrows;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.*;
//
//
//@ExtendWith(MockitoExtension.class)
//public class TypeEventServiceUnitTest {
//    @InjectMocks
//    private TypeEventService typeEventService;
//    @Mock
//    private TypeEventRepo typeEventRepo;
//    private TypeEvent typeEvent1;
//    private TypeEvent typeEvent2;
//    private List<TypeEvent> eventList;
//
//    @BeforeEach
//    public void setUp() {
//        typeEvent1 = new TypeEvent(1L, "testEvent1");
//        typeEvent2 = new TypeEvent(2L, "testEvent2");
//        eventList = new ArrayList<>(Arrays.asList(typeEvent1, typeEvent2));
//    }
//
//    @AfterEach
//    public void tearDown() {
//        typeEvent1 = typeEvent2 = null;
//        eventList = null;
//    }
//
//    @Test
//    public void getEventByIdTest() {
//        when(typeEventRepo.findById(any())).thenReturn(java.util.Optional.of(typeEvent1));
//        assertEquals(typeEventService.getEventById(typeEvent1.getId()), typeEvent1);
//        verify(typeEventRepo, times(1)).findById(any());
//    }
//    @Test
//    public void getEventByIdWhichNotExistTest() {
//        Exception exception = assertThrows(ResourceNotFoundException.class,() -> typeEventService.getEventById(typeEvent1.getId()));
//        assertEquals(exception.getMessage(), "Type event with ID number '" + typeEvent1.getId()+ "' does not exist");
//        verify(typeEventRepo, times(1)).findById(any());
//    }
//
//    @Test
//    public void getAllEventsTest() {
//        when(typeEventRepo.findAll()).thenReturn(eventList);
//        assertEquals(typeEventService.getAllEvents(), eventList);
//        verify(typeEventRepo, times(1)).findAll();
//    }
//
//    @Test
//    public void createEventTest() {
//        when(typeEventRepo.save(any())).thenReturn(typeEvent1);
//        assertEquals(typeEventService.createEvent(typeEvent1), typeEvent1);
//        verify(typeEventRepo, times(1)).save(any());
//    }
//
//    @Test
//    public void createEventThrowsAlreadyExistExceptionTest() {
//        when(typeEventRepo.findByType(any())).thenReturn(typeEvent1);
//        assertThrows(EntityAlreadyExistException.class, () -> typeEventService.createEvent(typeEvent1));
//        verify(typeEventRepo, times(1)).findByType(any());
//    }
//    @Test
//    public void deleteTypeEventByIdTest() {
//        when(typeEventRepo.findById(any())).thenReturn(java.util.Optional.of(typeEvent1));
//        assertEquals(typeEventService.deleteTypeEventById(typeEvent1.getId()).get("Event Deleted Successfully"), Boolean.TRUE);
//        verify(typeEventRepo, times(1)).findById(any());
//        verify(typeEventRepo, times(1)).delete(any());
//    }
//
//    @Test
//    public void updateTypeEventTest(){
//        when(typeEventRepo.findByType(any())).thenReturn(null);
//        when(typeEventRepo.findById(typeEvent1.getId())).thenReturn(java.util.Optional.ofNullable(typeEvent1));
//        assertEquals(typeEventService.updateTypeEvent(typeEvent1.getId(), typeEvent2)
//                .get("Event Update Successfully"), Boolean.TRUE);
//        verify(typeEventRepo, times(1)).save(any());
//        verify(typeEventRepo, times(1)).findByType(any());
//    }
//    @Test
//    public void updateTypeEventThrowsAlreadyExistTest(){
//        when(typeEventRepo.findByType(any())).thenReturn(typeEvent1);
//        when(typeEventRepo.findById(typeEvent1.getId())).thenReturn(java.util.Optional.ofNullable(typeEvent1));
//        assertThrows(EntityAlreadyExistException.class, ()->typeEventService.updateTypeEvent(typeEvent1.getId(), typeEvent2));
//        verify(typeEventRepo, times(0)).save(any());
//        verify(typeEventRepo, times(1)).findByType(any());
//    }
//
//
//
//}