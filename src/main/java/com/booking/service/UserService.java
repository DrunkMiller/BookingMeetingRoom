package com.booking.service;

import com.booking.advice.ResourceNotFoundException;
import com.booking.models.User;
import com.booking.repositories.UserRepo;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserService implements UserDetailsService {
    private final UserRepo userRepo;

    public UserService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        User user = userRepo.findByLogin(login);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return user;
    }

    public User findUserById(Long userId) {
        return userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("An employee with ID " + userId + " not found"));
    }

    public Map<String, Boolean> updateUser(Long userId, User userNew) {
        User userOld = findUserById(userId);
        userOld.setFirstname(userNew.getFirstname());
        userOld.setSecondname(userNew.getSecondname());
        userOld.setPassword(userNew.getPassword());
        userOld.setRoles(userNew.getRoles());
        userRepo.save(userOld);
        Map<String, Boolean> map = new HashMap<>();
        map.put("User parameters updated successfully", Boolean.TRUE);
        return map;
    }

    public Map<String, Boolean> deleteUserById(Long userId) {
        User user = findUserById(userId);
        userRepo.delete(user);
        Map<String, Boolean> map = new HashMap<>();
        map.put("User Deleted Successfully", Boolean.TRUE);
        return map;
    }

    public List<User> getAllUsers() {
        return userRepo.findAll();
    }
}