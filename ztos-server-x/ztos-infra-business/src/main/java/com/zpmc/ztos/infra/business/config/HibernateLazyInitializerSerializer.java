package com.zpmc.ztos.infra.business.config;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.hibernate.proxy.pojo.javassist.JavassistLazyInitializer;

import java.io.IOException;

public final class HibernateLazyInitializerSerializer extends JsonSerializer<JavassistLazyInitializer> {
	public static final HibernateLazyInitializerSerializer INSTANCE = new HibernateLazyInitializerSerializer();

    private HibernateLazyInitializerSerializer() {}
    @Override
    public void serialize(JavassistLazyInitializer initializer, JsonGenerator jsonGenerator,
                          SerializerProvider serializerProvider)
            throws IOException, JsonProcessingException {
        jsonGenerator.writeNull();
    }
}
