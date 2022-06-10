package org.argonath.rd.entry.util;

public class Assert {
    public static void notNull(Object obj, String message) {
        if (obj == null) {
            throw new IllegalArgumentException(message);
        }
    }

    public static void isTrue(Boolean expression, String message) {
        if (!expression) {
            throw new IllegalArgumentException(message);
        }
    }
}
