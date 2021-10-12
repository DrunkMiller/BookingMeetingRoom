package com.booking.controllers;

import com.booking.models.User;
import com.booking.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Secured("ROLE_ADMIN")
    @GetMapping()
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> getUserById(@PathVariable(value = "id") Long userId, Authentication currentUser) {
        if (methodAccess(currentUser, userId))
            return ResponseEntity.ok(userService.getUserById(userId));
        else return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    @PostMapping("/registration")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User userNew = userService.createUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(userNew);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deleteUserById(@PathVariable(value = "id") Long userId, Authentication currentUser) {
        if (methodAccess(currentUser, userId)) {
            userService.deleteUserById(userId);
            return ResponseEntity.ok().build();
        } else return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable(value = "id") Long userId, @RequestBody User userNew, Authentication currentUser) {
        if (methodAccess(currentUser, userId)) {
            userService.updateUser(userId, userNew);
            return ResponseEntity.ok().build();
        } else return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    private boolean methodAccess(Authentication currentUser, Long userId) {
        User user = userService.getByUsername(currentUser.getName());
        boolean hasRoleAdmin = user.getRoles().stream().anyMatch(s -> s.getName().equals("ROLE_ADMIN"));
        return hasRoleAdmin || userId.equals(user.getId());
    }

}
