package com.yunjae.springreactivecassandra.domain;

import com.datastax.driver.core.DataType;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.util.UUID;

@Table("greeting")
@Data
@AllArgsConstructor
public class Greeting {
    @PrimaryKey
    @CassandraType(type = DataType.Name.UUID)
    private UUID id;

    private String text;

    public Greeting() {
        id = UUID.randomUUID();
    }

    public Greeting(String text) {
        id = UUID.randomUUID();
        this.text = text;
    }
}
