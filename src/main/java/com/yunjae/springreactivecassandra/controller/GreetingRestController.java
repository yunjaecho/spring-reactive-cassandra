package com.yunjae.springreactivecassandra.controller;

import com.yunjae.springreactivecassandra.domain.Greeting;
import org.reactivestreams.Publisher;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.time.Instant;

@RestController
public class GreetingRestController {

    @GetMapping(value = "/see", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    Publisher<Greeting> seeGreetings() {
        return Flux
                .<Greeting>generate(sink -> sink.next(new Greeting("Hello, world @ " + Instant.now().toString())))
                .delayElements(Duration.ofSeconds(1));
    }

    @GetMapping("/greeting")
    Publisher<Greeting> greeting() {
        return Mono.just(new Greeting("Hello, world!"));
    }

    @GetMapping("/greetings")
    Publisher<Greeting> greetings() {
        Flux<Greeting> greetingFlux = Flux
                .<Greeting>generate(sink -> sink.next(new Greeting("hello, world")))
                .take(1000);


        return greetingFlux;
    }





}
