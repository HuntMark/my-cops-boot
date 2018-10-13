package com.example.orm.jpa;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class InMemoryUniqueIdGenerator implements UniqueIdGenerator<UUID> {
    @Override
    public UUID getNextUniqueId() {
        return UUID.randomUUID();
    }
}
