package com.example.demo.controller;

import com.example.demo.dto.UserDto;
import com.example.demo.dto.mapper.UserMapper;
import com.example.demo.service.UserService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("public")
public class PublicController {
    private final UserService userService;
    private final UserMapper userMapper;

    public PublicController(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }
    @PostMapping("/demo-user")
    public Mono<UserDto> newUser(@RequestBody UserDto userDto) {
        var user = userMapper.map(userDto);
        return userService.createUser(user)
                .map(u -> userMapper.map(u));
    }

    @GetMapping("/version")
    public Mono<String> version() {
        return Mono.just("1.0.0");
    }
}
