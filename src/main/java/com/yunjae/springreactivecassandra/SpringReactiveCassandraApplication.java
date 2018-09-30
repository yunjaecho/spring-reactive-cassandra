package com.yunjae.springreactivecassandra;

import com.yunjae.springreactivecassandra.domain.Reservation;
import com.yunjae.springreactivecassandra.repository.ReservationRepository;
import lombok.extern.java.Log;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@SpringBootApplication
public class SpringReactiveCassandraApplication {

    public static void main(String[] args) throws InterruptedException {
        SpringApplication.run(SpringReactiveCassandraApplication.class, args);
        Thread.sleep(3000);
    }
}

@Log
@Component
class DataInitializer implements ApplicationRunner {

    @Autowired
    private ReservationRepository repository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Flux
                .just("Pete", "Julie", "Josh", "Marcin", "Phil")
                .map(name -> new Reservation(name))
                .flatMap(this.repository::save)
                .thenMany(repository.findAll())
                //.subscribeOn(Schedulers.fromExecutor(Executors.newSingleThreadExecutor()))
                .subscribe(System.out::println);

        //Thread.sleep(1000);

        //repository.findAll().subscribe(System.out::println);


        /*Flux<String> names = Flux.just("Pete", "Julie", "Josh", "Marcin", "Phil");
        Flux<Reservation> reservationFlux = names.map(name -> new Reservation(name));
        Flux<Reservation> map = reservationFlux.flatMap(this.repository::save);
        map.subscribe(System.out::println);

        repository.findAll().subscribe(System.out::println);*/

        /*
        map.subscribe(new Subscriber<Reservation>() {
            @Override
            public void onSubscribe(Subscription subscription) {
                log.info("onSubscribe ( " + subscription.toString() + ")");
                subscription.request(10);
            }

            @Override
            public void onNext(Reservation reservation) {
                log.info("new reservation : " + reservation.toString());
            }

            @Override
            public void onError(Throwable throwable) {
                log.info("oops! ");
                log.info(throwable.toString());
            }

            @Override
            public void onComplete() {
                log.info("complete");
            }
        });
        */
    }
}
