package com.bookingsystem.booking.shared.crypto;

import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class HmacSha256TokenHasher implements TokenHasher{
    
    private final byte[] key;
    
    public HmacSha256TokenHasher(String secret) {
        
        if (secret == null || secret.isBlank()) {
        throw new IllegalStateException("REFRESH_HASH_SECRET (security.refresh.hash-secret) is missing");
        }
        this.key = secret.getBytes(StandardCharsets.UTF_8);
    }

    @Override
    public String hash(String raw) {
        try {
            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(new SecretKeySpec(key, "HmacSHA256"));
            byte[] h = mac.doFinal(raw.getBytes(StandardCharsets.UTF_8));
            return Base64.getUrlEncoder().withoutPadding().encodeToString(h); 
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("HMAC SHA-256 algorithm not found", e);
        } catch (InvalidKeyException e) {
            throw new IllegalStateException("Invalid HMAC SHA-256 key", e); 
        } 
    }

    @Override
    public boolean matches(String raw, String hashed) {
        return hash(raw).equals(hashed);
    }
}
