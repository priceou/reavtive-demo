package com.reactivedemo.demo01.service;

import com.reactivedemo.demo01.entity.User;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface UserRepository extends ReactiveMongoRepository<User, String> {

    public Flux<User> findByAgeBetween(int start, int end);

    @Query(value = "db.user.find({age:{$gte:20, $lte:30}})")
    Flux<User> oldUser();
}
