package com.example.demo.config.security;

import com.example.demo.config.security.auth.UserPrincipal;
import com.example.demo.exception.UnauthorizedException;
import com.example.demo.service.UserService;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
@Component
public class AuthenticationManager implements ReactiveAuthenticationManager {

    private final UserService userService;

    public AuthenticationManager(UserService userService) {
        this.userService = userService;
    }

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {

        var principal = (UserPrincipal) authentication.getPrincipal();



        return userService.getUser(principal.getId())
                .filter(user -> user.isEnabled())
                .switchIfEmpty(Mono.error(new UnauthorizedException("User account is disabled")))
                .map(user -> authentication);
    }
}
