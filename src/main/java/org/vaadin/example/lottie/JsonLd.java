package org.vaadin.example.lottie;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.time.LocalDate;

/**
 * a Tiny JsonLd domain model with Jackson serialization, https://json-ld.org/
 */
public record JsonLd(@JsonProperty("@context") String context, @JsonProperty("@type") String type, String name, String description, String url, LocalDate datePublished, Image image) {

    public static JsonLd webSite(String name, String description, String url, String imageUrl) {
        return new JsonLd("https://schema.org", "WebPage", name, description, url, LocalDate.now(), new Image("ImageObject", imageUrl, name));
    }

    public record Image(@JsonProperty("@type") String type, String contentUrl, String caption) {
    }

    private static ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

    String toJson() {
        try {
            return objectMapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}

