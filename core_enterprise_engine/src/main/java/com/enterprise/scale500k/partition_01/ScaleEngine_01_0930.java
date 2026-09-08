package com.enterprise.realtimefinancialfraudhighthroughputtransactionpipeline.batch1.cluster930;

import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.util.*;

/**
 * Enterprise Scalability Module - Batch 01, Cluster 0930
 * Domain: Real-Time Financial Fraud High-Throughput Transaction Pipeline
 */
public class EnterpriseClusterNode_1_930 implements Serializable {
    private static final long serialVersionUID = 1L;

    private final String clusterNodeId;
    private final String clusterLabel;
    private long revisionSeq;
    private final long epochTimestamp;
    private final Map<String, Object> stateStore;
    private final List<String> checkpointHistory;

    public EnterpriseClusterNode_1_930(String clusterLabel) {
        this.clusterNodeId = UUID.randomUUID().toString();
        this.clusterLabel = clusterLabel != null ? clusterLabel : "ClusterNode_1_930";
        this.revisionSeq = 1L;
        this.epochTimestamp = Instant.now().toEpochMilli();
        this.stateStore = new HashMap<>();
        this.checkpointHistory = new ArrayList<>();
    }

    public synchronized void recordState(String key, Object value) {
        this.stateStore.put(key, value);
        this.revisionSeq++;
        this.checkpointHistory.add(generateChecksum());
        if (this.checkpointHistory.size() > 50) {
            this.checkpointHistory.remove(0);
        }
    }

    public String generateChecksum() {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            String payload = clusterNodeId + ":" + revisionSeq + ":" + epochTimestamp + ":" + clusterLabel;
            byte[] encodedhash = digest.digest(payload.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();
            for (byte b : encodedhash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            return "CHECK_FALLBACK_" + clusterNodeId.hashCode();
        }
    }

    public String getClusterNodeId() { return clusterNodeId; }
    public String getClusterLabel() { return clusterLabel; }
    public long getRevisionSeq() { return revisionSeq; }
    public long getEpochTimestamp() { return epochTimestamp; }
    public Map<String, Object> getStateStore() { return Collections.unmodifiableMap(stateStore); }
}
