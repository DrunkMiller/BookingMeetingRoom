package com.booking.controllers;

import com.booking.dto.AuthenticationUserDto;
import com.booking.dto.AuthenticationUserTokenDto;
import com.booking.models.User;
import com.booking.security.jwt.JwtTokenProvider;
import com.booking.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(value = "/login")
public class AuthenticationController {
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;

    @Autowired
    public AuthenticationController(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userService = userService;
    }

    @PostMapping("")
    public ResponseEntity<AuthenticationUserTokenDto> login(@RequestBody AuthenticationUserDto requestDto) {
        String username = requestDto.getUsername();
        log.info("The user with the login \"{}\" starts authentication.", requestDto.getUsername());
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                username, requestDto.getPassword()));
        User user = userService.getByUsername(username);
        String token = jwtTokenProvider.createToken(username, user.getRoles());
        AuthenticationUserTokenDto response = new AuthenticationUserTokenDto(username, token);
        log.info("The user with the login \"{}\" successfully authenticated.", requestDto.getUsername());
        return ResponseEntity.ok(response);
    }
}
