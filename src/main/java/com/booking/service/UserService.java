package com.booking.service;

import com.booking.advice.EntityAlreadyExistException;
import com.booking.advice.ResourceNotFoundException;
import com.booking.dto.UserDto;
import com.booking.mapper.Convertor;
import com.booking.models.postgres.Role;
import com.booking.models.postgres.User;
import com.booking.repositories.postgres.RoleRepo;
import com.booking.repositories.postgres.UserRepo;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Validated
public class UserService {
    private final UserRepo userRepo;
    private final RoleRepo roleRepo;
    private final PasswordEncoder passwordEncoder;
    private final Convertor convertor;

    public UserService(UserRepo userRepo, RoleRepo roleRepo, PasswordEncoder passwordEncoder, Convertor convertor) {
        this.userRepo = userRepo;
        this.roleRepo = roleRepo;
        this.passwordEncoder = passwordEncoder;
        this.convertor = convertor;
    }

    public User getByUsername(String username) {
        User userFromDb = userRepo.findByUsername(username);
        if (userFromDb == null) {
            throw new ResourceNotFoundException("An employee with this login: '" + username + "' not found");
        }
        return userFromDb;
    }

    public List<User> getAllUsers() {
        return userRepo.findAll();
    }

    public User getUserById(Long userId) {
        return userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("An employee with ID " + userId + " not found"));
    }
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public UserDto createUser(@Valid User user) {
        if (hasUserByLogin(user.getUsername())) {
            List<Role> roles = user.getRoles().stream()
                    .map(role -> roleRepo.findByName(role.getName()))
                    .collect(Collectors.toList());
            roles.add(roleRepo.findByName("ROLE_USER"));
            if (roles.contains(roleRepo.findByName("ROLE_ADMIN"))){
                throw new EntityAlreadyExistException("Can't create a user with admin rights");
            }
            user.setRoles(roles);
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepo.saveAndFlush(user);
        }
        return convertor.convertToDto(user, UserDto.class);
    }
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void updateUser(Long userId, @Valid User userNew) {
        User userOld = getUserById(userId);
        if (userOld.getUsername().equals(userNew.getUsername()) || hasUserByLogin(userNew.getUsername())) {
            userOld.setFirstName(userNew.getFirstName());
            userOld.setLastName(userNew.getLastName());
            userOld.setUsername(userNew.getUsername());
            userOld.setPassword(passwordEncoder.encode(userNew.getPassword()));
            userOld.setRoles(userNew.getRoles());
            userRepo.save(userOld);
        }
    }
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public void deleteUserById(Long userId) {
        User user = getUserById(userId);
        userRepo.delete(user);
    }

    private boolean hasUserByLogin(String login) {
        User userFromDb = userRepo.findByUsername(login);
        if (userFromDb != null) {
            throw new EntityAlreadyExistException("An employee with this login: '" + login + "' already exist!");
        }
        return true;
    }
}