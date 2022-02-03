package com.example.demo.service;

import com.example.demo.exception.AlreadyExistsException;
import com.example.demo.model.user.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.security.SecurityService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.UUID;

@Service
public class UserService {
//    private final SecurityService securityService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private static final Logger LOGGER = LogManager.getLogger();

    public UserService( UserRepository userRepository, PasswordEncoder passwordEncoder) {

        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Mono<User> createUser(User user){
   // final String username = user.getUsername();

    return userRepository.save(user.toBuilder()
            .password(passwordEncoder.encode(user.getPassword()))
            .id(user.setId(UUID.randomUUID().toString()))
            .roles(Collections.singletonList("ROLE_USER"))
            .enabled(true)
            .createdAt(LocalDateTime.now())
            .build())
            .doOnSuccess(u -> LOGGER.info("Created new user with ID = " +  u.getId()));
//        return userRepository
//                .findByUsernameAndDeleted(username)
//                .flatMap(foundUser -> {
//                            if (!foundUser) {
//
//
//
//                                userRepository.save(user.toBuilder()
//                                        .password(passwordEncoder.encode(user.getPassword()))
//                                        .id(user.setId(UUID.randomUUID().toString()))
//                                        .roles(Collections.singletonList("ROLE_USER"))
//                                        .enabled(true)
//                                        .createdAt(LocalDateTime.now())
//                                        .build());
////
//                                return userRepository.save( user.toBuilder().password(passwordEncoder.encode(user.getPassword()))
//                                        .id(user.setId(UUID.randomUUID().toString()))
//                                        .roles(Collections.singletonList("ROLE_USER"))
//                                        .enabled(true)
//                                        .createdAt(LocalDateTime.now())
//                                        .build());
////);
//                            } else {
//                                final String errorMsg = String.format("%s already exist in the system", username);
//                                LOGGER.error(errorMsg);
//                                return Mono.error(new AlreadyExistsException(errorMsg));
//                            }
//                        }
//
//                ).doOnSuccess(
//                        userResponse ->
//                                LOGGER.info(  "Following user with [{}] username has signed up" , username));
//



//          return       userRepository.save(user.toBuilder()
//                .password(passwordEncoder.encode(user.getPassword()))
//                .id(user.setId(UUID.randomUUID().toString()))
//                .roles(Collections.singletonList("ROLE_USER"))
//                .enabled(true)
//                .createdAt(LocalDateTime.now())
//                .build())
//                .doOnSuccess(user1 -> LOGGER.info("Created user with ID " + user1.getId()) );



    }
    public Mono<User> getUser(String userId){
        return userRepository.findById(userId);
    }
}
