package com.yunjae.springreactivecassandra.domain;

import com.datastax.driver.core.DataType;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.util.UUID;

@Table("reservation")
@Data
@AllArgsConstructor
public class Reservation {
    @PrimaryKey
    @CassandraType(type = DataType.Name.UUID)
    private UUID id;

    private String reservationName;

    public Reservation() {
        id = UUID.randomUUID();
    }

    public Reservation(String name) {
        id = UUID.randomUUID();
        reservationName = name;
    }
}
