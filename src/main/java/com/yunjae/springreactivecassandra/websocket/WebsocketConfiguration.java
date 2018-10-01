package com.yunjae.springreactivecassandra.websocket;

import com.yunjae.springreactivecassandra.domain.Greeting;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.HandlerMapping;
import org.springframework.web.reactive.handler.SimpleUrlHandlerMapping;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.WebSocketSession;
import org.springframework.web.reactive.socket.server.support.WebSocketHandlerAdapter;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.SynchronousSink;

import java.time.Duration;
import java.time.Instant;
import java.util.Collections;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@Configuration
public class WebsocketConfiguration {

    @Bean
    WebSocketHandlerAdapter webSocketHandlerAdapter() {
        return new WebSocketHandlerAdapter();
    }

    @Bean
    WebSocketHandler webSocketHandler() {
        return session -> {
            Flux<WebSocketMessage> generate = Flux
                    .<Greeting>generate(sink -> sink.next(new Greeting("Hello @  " + Instant.now().toString())))
                    .map(g -> session.textMessage(g.getText()))
                    .delayElements(Duration.ofSeconds(1))
                    .doFinally(signalType -> System.out.println("goodbye"))
                    ;

            return session.send(generate);
        };
    }

    @Bean
    HandlerMapping handlerMapping() {
        SimpleUrlHandlerMapping simpleUrlHandlerMapping = new SimpleUrlHandlerMapping();
        simpleUrlHandlerMapping.setUrlMap(Collections.singletonMap("/ws/hell", webSocketHandler()));
        simpleUrlHandlerMapping.setOrder(10);
        return simpleUrlHandlerMapping;
    }



}






