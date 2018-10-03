package com.yunjae.springreactivecassandra.config;

import com.yunjae.springreactivecassandra.domain.Greeting;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.time.Instant;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;

@Configuration
public class FunctionalConfiguration {

    public FunctionalConfiguration() {
        System.out.println("FunctionalConfiguration.....");
    }

    Mono<ServerResponse> sse(ServerRequest serverRequest) {
        return ServerResponse
                .ok()
                .contentType(MediaType.TEXT_EVENT_STREAM)
                .body(
                    Flux
                        .<Greeting>generate(sink -> sink.next(new Greeting("Hello, world @ " + Instant.now().toString())))
                        .delayElements(Duration.ofSeconds(1))
                    , Greeting.class);
    }


    @Bean
    RouterFunction<ServerResponse> routes() {
        return RouterFunctions.route(GET("/frp/greetings"), serverRequest -> ServerResponse.ok().body(Flux.just("hello, world"), String.class))
            .andRoute(GET("/frp/hi"), serverRequest -> ServerResponse.ok().body(Flux.just("Hi"), String.class))
            .andRoute(GET("/frp/sse"), this::sse);
        /*.andRoute(GET("/frp/see"), serverRequest -> ServerResponse.ok().contentType(MediaType.TEXT_EVENT_STREAM).body(
                Flux
                        .<Greeting>generate(sink -> sink.next(new Greeting("Hello, world @ " + Instant.now().toString())))
                        .delayElements(Duration.ofSeconds(1))
                , Greeting.class));*/
        //return RouterFunctions.route(GET("/othergreetings").or(GET("/hello")), serverRequest -> ServerResponse.ok().body(Flux.just("hello, world"), String.class));
        //return RouterFunctions.route(request -> (Math.random() > .5), serverRequest -> ServerResponse.ok().body(Flux.just("hello, world"), String.class));
    }
}
