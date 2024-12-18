package org.vaadin.example.lottie;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Test;

public class JsonLdTest {

    @Test
    void testSerialization() throws JsonProcessingException {

        var appName = "Vaadin Lottie Demo application";
        var desc = "Embed Lottie animations into your Vaadin Java application easily.";
        var url = "https://lottie-vaadin-demo.fly.dev/";
        var imageUrl = "https://lottie-vaadin-demo.fly.dev/lottie_social_preview.png";

        JsonLd jsonLd = JsonLd.webSite(appName, desc, url, imageUrl);

        ObjectWriter objectWriter = new ObjectMapper()
                .registerModule(new JavaTimeModule())
                .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
                .writerWithDefaultPrettyPrinter();
        System.out.println(objectWriter.writeValueAsString(jsonLd));

    }
}
