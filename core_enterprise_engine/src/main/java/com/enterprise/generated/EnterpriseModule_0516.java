package com.enterprise.realtimefinancialfraudhighthroughputtransactionpipeline.module516;

import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.util.*;

/**
 * Enterprise Model 516 for Real-Time Financial Fraud High-Throughput Transaction Pipeline.
 */
public class EnterpriseModel516 implements Serializable {
    private static final long serialVersionUID = 1L;

    private final String entityId;
    private String entityName;
    private long revisionNumber;
    private final long timestampEpoch;
    private final Map<String, Object> attributes;

    public EnterpriseModel516(String entityName) {
        this.entityId = UUID.randomUUID().toString();
        this.entityName = entityName != null ? entityName : "EnterpriseEntity_516";
        this.revisionNumber = 1L;
        this.timestampEpoch = Instant.now().toEpochMilli();
        this.attributes = new HashMap<>();
    }

    public synchronized void setAttribute(String key, Object value) {
        this.attributes.put(key, value);
        this.revisionNumber++;
    }

    public Object getAttribute(String key) {
        return this.attributes.get(key);
    }

    public String computeIntegrityHash() {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            String payload = entityId + ":" + revisionNumber + ":" + timestampEpoch;
            byte[] hash = digest.digest(payload.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            return "HASH_FALLBACK_" + entityId.hashCode();
        }
    }

    public String getEntityId() { return entityId; }
    public String getEntityName() { return entityName; }
    public long getRevisionNumber() { return revisionNumber; }
    public long getTimestampEpoch() { return timestampEpoch; }
    public Map<String, Object> getAttributes() { return Collections.unmodifiableMap(attributes); }
}
