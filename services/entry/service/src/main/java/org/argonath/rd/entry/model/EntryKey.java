package org.argonath.rd.entry.model;

import org.argonath.rd.entry.util.HashKeyGenerator;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.json.stream.JsonParsingException;
import java.util.Map;

public class EntryKey {
    private static Jsonb JSON_BUILDER = JsonbBuilder.create();

    public static String keyHash(String key) {
        return HashKeyGenerator.hash(key);
    }

    public enum KeyType {SIMPLE, COMPOSITE}

    public static Map<String, String> compositeKey(String key) {
        try {
            Map<String, String> keyMap = JSON_BUILDER.fromJson(key, Map.class);
            return keyMap;
        } catch (JsonParsingException e) {
            throw new RuntimeException("Cannot Parse Composite Key: " + key);
        }
    }
}
