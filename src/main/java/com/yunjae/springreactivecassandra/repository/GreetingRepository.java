package com.yunjae.springreactivecassandra.repository;

import com.yunjae.springreactivecassandra.domain.Greeting;
import org.springframework.data.cassandra.repository.ReactiveCassandraRepository;
import java.util.UUID;

public interface GreetingRepository extends ReactiveCassandraRepository<Greeting, UUID> {
}
