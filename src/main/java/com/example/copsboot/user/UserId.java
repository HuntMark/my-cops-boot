package com.example.copsboot.user;

import com.example.orm.jpa.AbstractEntityId;

import java.util.UUID;

public class UserId extends AbstractEntityId<UUID> {

    @SuppressWarnings("unused")
    protected UserId() {
    }

    public UserId(UUID id) {
        super(id);
    }
}
