package com.reactivedemo.demo01.handler;

import com.reactivedemo.demo01.entity.User;
import com.reactivedemo.demo01.service.UserRepository;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class UserHandler {

    private final UserRepository repository;
    public UserHandler(UserRepository userRepository){
        this.repository = userRepository;
    }

    public Mono<ServerResponse> getAllUser(ServerRequest request){
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON_UTF8)
                .body(this.repository.findAll(), User.class);
    }

    public Mono<ServerResponse> createUser(ServerRequest request){
        Mono<User> user = request.bodyToMono(User.class);

        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON_UTF8)
                .body(this.repository.saveAll(user), User.class);
    }

    public Mono<ServerResponse> deleteUser(ServerRequest request){
        String id = request.pathVariable("id");

        return this.repository.findById(id).flatMap(user -> this.repository.delete(user).then(ServerResponse.ok().build()))
                .switchIfEmpty(ServerResponse.notFound().build());
    }
}
