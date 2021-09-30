package com.booking.unit.service;

import com.booking.advice.EntityAlreadyExistException;
import com.booking.advice.ResourceNotFoundException;
import com.booking.models.Role;
import com.booking.models.User;
import com.booking.repositories.UserRepo;
import com.booking.service.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.event.annotation.BeforeTestClass;

import java.util.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceUnitTest {
    @InjectMocks
    private UserService userService;

    @Mock private UserRepo userRepo;
    private User user1;
    private User user2;
    private User user3ForUpdate;
    private List<User> userList;

    @BeforeEach
    public  void setUp(){
        user1 = new User(1L, "Ivan", "Ivanov", "ivan21", "123s", new HashSet<>(Collections.singletonList(Role.USER)));
        user2 = new User(2L, "Petr", "Petrov", "pert1", "q987", new HashSet<>(Arrays.asList(Role.LECTOR, Role.USER)));
        user3ForUpdate = new User(1L, "Nikita", "Ivanov", "pert1", "12s", new HashSet<>(Collections.singletonList(Role.USER)));
        userList = new ArrayList<>(Arrays.asList(user1, user2));
    }
    @AfterEach
    public void tearDown() {
        user1 = user2 = null;
        userList =null;
    }

    @Test
    public void getAllUserTest(){
        when(userRepo.findAll()).thenReturn(userList);
        assertEquals(userService.getAllUsers(), userList);
        verify(userRepo, times(1)).findAll();
    }

    @Test
    public void getUserByIdTest(){
        when(userRepo.findById(user1.getId())).thenReturn(Optional.ofNullable(user1));
        assertEquals(userService.getUserById(user1.getId()), user1);
        verify(userRepo, times(1)).findById(any());
    }
    @Test
    public void getUserByIdThrowsResourceNotFoundExceptionTest(){
        Exception exception = assertThrows(ResourceNotFoundException.class,() -> userService.getUserById(user1.getId()));
        assertEquals(exception.getMessage(), "An employee with ID " + user1.getId()+ " not found");
        verify(userRepo, times(1)).findById(any());
    }
    @Test
    public void createUserTest() {
        when(userRepo.save(user1)).thenReturn(user1);
        when(userRepo.findByLogin(any())).thenReturn(null);
        assertEquals(userService.createUser(user1), user1);
        verify(userRepo, times(1)).save(user1);
    }
    @Test
    public void createUserThrowsAlreadyExistExceptionTest() {
        when(userRepo.findByLogin(any())).thenReturn(user1);
        assertThrows(EntityAlreadyExistException.class, ()-> userService.createUser(user1));
        verify(userRepo, times(0)).save(user1);
    }
    @Test
    public void updateUserTest(){
        when(userRepo.findById(any())).thenReturn(Optional.ofNullable(user1));
        when(userRepo.findByLogin(any())).thenReturn(null);
        assertEquals(userService.updateUser(user1.getId(), user2)
                .get("User parameters updated successfully"), Boolean.TRUE);
        verify(userRepo, times(1)).save(any());
        verify(userRepo, times(1)).findByLogin(any());
        verify(userRepo, times(1)).findById(any());
    }
    @Test
    public void updateUserThrowsAlreadyExistTest(){
        when(userRepo.findById(any())).thenReturn(Optional.ofNullable(user1));
        when(userRepo.findByLogin(any())).thenReturn(user2);
        assertThrows(EntityAlreadyExistException.class,()->userService.updateUser(user1.getId(), user3ForUpdate)
                .get("An employee with this login: '"+user3ForUpdate.getLogin() +"' already exist!"));
        verify(userRepo, times(0)).save(any());
        verify(userRepo, times(1)).findByLogin(any());
        verify(userRepo, times(1)).findById(any());
    }

    @Test
    public void deleteUserByIdTest() {
        when(userRepo.findById(any())).thenReturn(java.util.Optional.of(user1));
        assertEquals(userService.deleteUserById(user1.getId()).get("User Deleted Successfully"), Boolean.TRUE);
        verify(userRepo, times(1)).findById(any());
        verify(userRepo, times(1)).delete(any());
    }
}
