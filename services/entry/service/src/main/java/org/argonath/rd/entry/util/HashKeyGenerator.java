package org.argonath.rd.entry.util;

import javax.xml.bind.DatatypeConverter;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashKeyGenerator {

    private static MessageDigest MD;

    static {
        try {
            MD = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Invalid Hash Algorithm", e);
        }
    }

    public static String hash(String value) {
        byte[] valueBytes = value.getBytes(StandardCharsets.UTF_8);
        byte[] hash = MD.digest(valueBytes);
        String hashString = DatatypeConverter.printHexBinary(hash);
        return hashString;
    }

    public static void main(String[] args) {
        System.out.println(hash("alex"));
        System.out.println(hash("alex"));
    }
}
