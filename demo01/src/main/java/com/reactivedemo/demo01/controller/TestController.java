package com.reactivedemo.demo01.controller;

import com.reactivedemo.demo01.entity.User;
import com.reactivedemo.demo01.service.UserRepository;
import io.netty.handler.codec.http.HttpResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

//@RestController
@Slf4j
public class TestController {

    private final UserRepository repository;
    public TestController(UserRepository repository){
        this.repository = repository;
    }

    @GetMapping("/get2")
    private Mono<String> get2(){
        log.info("do some string start");
        Mono<String> result = Mono.fromSupplier(() ->sleep());
        log.info("do some string end");
        return result;

    }

    private String  sleep(){
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "some string";
    }

    @GetMapping(value = "/getFlux1", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    private Flux<String> flux(){
        return Flux.fromStream(IntStream.range(0,5).mapToObj(i ->{
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "flux data--"+i;
        }));
    }

    @GetMapping("/user/get")
    private Flux<User> getUsers(@ModelAttribute("maMessage") String maMessage){
        System.out.println("/user/get model attibute maMessage"+ maMessage);
        return repository.findAll();
    }

    @GetMapping("/user/getById/{id}")
    private Mono<ResponseEntity<User>> getUserById(@PathVariable("id") String id){
        return repository.findById(id).map(user -> new ResponseEntity<>(user, HttpStatus.OK))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/user/save")
    private Mono<User> saveUser(@Valid @RequestBody User user){
        return repository.save(user);
    }

    @DeleteMapping("/user/delete/{id}")
    public Mono<ResponseEntity<Void>> deleteUser(@PathVariable("id") String id){
        return this.repository.findById(id)
                .flatMap(user -> this.repository
                                    .delete(user)
                                    .then(Mono.just(new ResponseEntity<Void>(HttpStatus.OK)))

                )
                .defaultIfEmpty(new ResponseEntity(HttpStatus.NOT_FOUND));

    }

    @PutMapping("/user/update/{id}")
    public Mono<ResponseEntity<User>> updateUser(@PathVariable("id") String id, @RequestBody User user){
        return this.repository.findById(id).flatMap(u -> {
            u.setName(user.getName());
            u.setAge(user.getAge());
            return this.repository.save(u);
        }).map(u -> {
            return new ResponseEntity<User>(u, HttpStatus.OK);
        }).defaultIfEmpty(new ResponseEntity(HttpStatus.NOT_FOUND));
    }


}
