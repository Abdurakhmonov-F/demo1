package com.example.demo.repository;

import com.example.demo.model.user.User;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;
@Repository
public interface UserRepository extends ReactiveMongoRepository<User, String> {
    
    Mono<User> findByUsername(String username);

//    @Query(value = "{'username': ?0 , enabled : false}" , exists = true)
//    Mono<Boolean> findByUsernameAndDeleted(String username);

}
