// File: src/main/java/com/project/finnote/utils/JsonUtils.java
package com.project.finnote.utils;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class JsonUtils {
    public static final ObjectMapper MAPPER = create();

    private static ObjectMapper create() {
        ObjectMapper mapper = new ObjectMapper();
        // Podrška za Java 8 Date/Time API
        mapper.registerModule(new JavaTimeModule());
        // Ignoriraj nepoznata polja
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return mapper;
    }

    private JsonUtils() { /* no instantiation */ }
}
