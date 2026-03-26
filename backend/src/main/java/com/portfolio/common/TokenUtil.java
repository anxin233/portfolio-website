package com.portfolio.common;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Base64;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class TokenUtil {

    private static final Map<String, Long> TOKEN_STORE = new ConcurrentHashMap<>();
    private static final long EXPIRE_MS = 24 * 60 * 60 * 1000L; // 24h

    public static String generateToken(Long userId) {
        String raw = userId + ":" + System.currentTimeMillis() + ":" + Math.random();
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(raw.getBytes(StandardCharsets.UTF_8));
            String token = Base64.getUrlEncoder().withoutPadding().encodeToString(hash);
            TOKEN_STORE.put(token, System.currentTimeMillis());
            return token;
        } catch (Exception e) {
            throw new RuntimeException("Token generation failed", e);
        }
    }

    public static boolean validate(String token) {
        if (token == null) return false;
        Long createdAt = TOKEN_STORE.get(token);
        if (createdAt == null) return false;
        if (System.currentTimeMillis() - createdAt > EXPIRE_MS) {
            TOKEN_STORE.remove(token);
            return false;
        }
        return true;
    }

    public static void invalidate(String token) {
        if (token != null) TOKEN_STORE.remove(token);
    }

    public static String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(password.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(hash);
        } catch (Exception e) {
            throw new RuntimeException("Hash failed", e);
        }
    }
}
