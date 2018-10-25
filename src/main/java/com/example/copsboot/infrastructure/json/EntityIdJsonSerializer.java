package com.example.copsboot.infrastructure.json;

import com.example.orm.jpa.EntityId;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;
import org.springframework.boot.jackson.JsonComponent;

import java.io.IOException;

@JsonComponent
public class EntityIdJsonSerializer extends JsonSerializer<EntityId> {
    @Override
    public void serialize(EntityId value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
        jgen.writeString(value.asString());
    }
}
