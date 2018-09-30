package com.yunjae.springreactivecassandra.repository;

import com.yunjae.springreactivecassandra.domain.Reservation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.cassandra.repository.ReactiveCassandraRepository;
import reactor.core.publisher.Flux;

import java.util.UUID;

public interface ReservationRepository extends ReactiveCassandraRepository<Reservation, UUID> {
    Flux<Reservation> findByReservationName(String name);
}
