package com.salon.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
//configurazione per la serializzazione e deserializzazione date e ora
@Configuration
public class JacksonConfig {

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        // Supporto per Java 8 Date/Time API
        mapper.registerModule(new JavaTimeModule());
        // Serializza Instant come stringa ISO-8601
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        // Modulo custom per deserializzazione flessibile di Instant
        SimpleModule simpleModule = new SimpleModule();
        simpleModule.addDeserializer(Instant.class, new JsonDeserializer<Instant>() {
            @Override
            public Instant deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
                String dateString = p.getText().trim();
                try {
                    return Instant.parse(dateString);
                } catch (Exception e) {
                    try {
                        LocalDate date = LocalDate.parse(dateString);
                        return date.atStartOfDay().toInstant(ZoneOffset.UTC);
                    } catch (Exception e2) {
                        throw new IOException("Formato di data non valido: " + dateString);
                    }
                }
            }
        });
        mapper.registerModule(simpleModule);

        return mapper;
    }
}
