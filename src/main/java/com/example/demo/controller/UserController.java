package com.example.demo.controller;

import com.example.demo.config.security.auth.UserPrincipal;
import com.example.demo.dto.UserDto;
import com.example.demo.dto.mapper.UserMapper;
import com.example.demo.service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("user")
@PreAuthorize("hasRole('USER')")
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper;

    public UserController(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }
    @GetMapping
    public Mono<UserDto> get(Authentication authentication){
        var principal = (UserPrincipal) authentication.getPrincipal();
        return userService.getUser(principal.getId())
                .map(user -> userMapper.map(user));
    }
}
