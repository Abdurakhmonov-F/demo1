package com.example.demo.controller;

import com.example.demo.dto.AuthResultDto;
import com.example.demo.dto.UserLoginDto;
import com.example.demo.service.security.SecurityService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class AuthController {
    private final SecurityService securityService;

    public AuthController(SecurityService securityService) {
        this.securityService = securityService;
    }

    @PostMapping("/login")
    public Mono<ResponseEntity<Object>>login(@RequestBody UserLoginDto dto){
        return securityService.authenticate(dto.getUsername(), dto.getPassword())
                .flatMap(tokenInfo -> Mono.just(ResponseEntity.ok(AuthResultDto.builder()
                        .id(tokenInfo.getUserId())
                        .token(tokenInfo.getToken())
                        .issueAt(tokenInfo.getIssuedAt())
                        .expiresAt(tokenInfo.getExpiresAt())
                        .build())));
    }
}
