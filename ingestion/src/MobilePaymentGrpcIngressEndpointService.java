package com.financial.pipeline.ingestion;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * Real-time service processor: MobilePaymentGrpcIngressEndpointService.
 * High-throughput gRPC protobuf streaming endpoint for Apple Pay, Google Pay, and UPI ingress.
 */
public class MobilePaymentGrpcIngressEndpointService {

    private static final Logger LOGGER = Logger.getLogger(MobilePaymentGrpcIngressEndpointService.class.getName());
    private final Map<String, MobilePaymentGrpcIngressEndpointModel> storageCache = new ConcurrentHashMap<>();
    private long processedCount = 0;
    private long fraudBlockedCount = 0;
    private double cumulativeLatencyNano = 0.0;
    private boolean isActive = true;

    public MobilePaymentGrpcIngressEndpointService() {
        seedInstitutionalState();
    }

    private void seedInstitutionalState() {
        for (int i = 1; i <= 6; i++) {
            String txnId = "TXN-INIT-STORE-" + i;
            MobilePaymentGrpcIngressEndpointModel model = new MobilePaymentGrpcIngressEndpointModel(
                txnId,
                "TOKEN-CARD-" + (2000 + i),
                "MERCHANT-" + (500 + i),
                "TERM-GATE-" + i,
                "USD",
                250.0 * i,
                0.03 * i,
                40.7128 + (i * 0.005),
                -74.0060 + (i * 0.005),
                12.5,
                "5411",
                "APPROVED",
                "CLEARED_NORMAL_VELOCITY",
                "Automated stream ingest verification baseline"
            );
            storageCache.put(txnId, model);
        }
    }

    public MobilePaymentGrpcIngressEndpointModel processIncomingStream(MobilePaymentGrpcIngressEndpointModel request) {
        long startNano = System.nanoTime();
        if (request == null || request.getTransactionId() == null) {
            LOGGER.log(Level.WARNING, "Rejected null transaction stream request in MobilePaymentGrpcIngressEndpointService");
            throw new IllegalArgumentException("Invalid transaction coordinates or payload");
        }

        LOGGER.info("Executing MobilePaymentGrpcIngressEndpointService for transaction: " + request.getTransactionId());

        double calculatedScore = 0.03;
        if (request.getTransactionAmount() != null && request.getTransactionAmount() > 3000.0) {
            calculatedScore += 0.35;
        }
        if ("6051".equals(request.getMerchantCategoryCode()) || "5944".equals(request.getMerchantCategoryCode())) {
            calculatedScore += 0.25;
        }

        // Check sliding window velocity
        long recentTxns = storageCache.values().stream()
                .filter(m -> Objects.equals(m.getTokenizedCardId(), request.getTokenizedCardId()))
                .count();

        if (recentTxns > 4) calculatedScore += 0.40;

        double finalScore = Math.min(1.0, calculatedScore);
        request.setRiskProbabilityScore(finalScore);

        String decision = finalScore >= 0.85 ? "BLOCKED" : (finalScore >= 0.60 ? "REVIEW" : "APPROVED");
        request.setOperationalStatus(decision);
        request.setProcessingTimestamp(LocalDateTime.now());

        storageCache.put(request.getTransactionId(), request);
        processedCount++;
        if ("BLOCKED".equals(decision)) fraudBlockedCount++;

        cumulativeLatencyNano += (System.nanoTime() - startNano);
        return request;
    }

    public Optional<MobilePaymentGrpcIngressEndpointModel> findById(String transactionId) {
        if (transactionId == null) return Optional.empty();
        return Optional.ofNullable(storageCache.get(transactionId));
    }

    public List<MobilePaymentGrpcIngressEndpointModel> listAll() {
        return new ArrayList<>(storageCache.values());
    }

    public List<MobilePaymentGrpcIngressEndpointModel> listBlockedFraud() {
        return storageCache.values().stream()
                .filter(m -> "BLOCKED".equals(m.getOperationalStatus()))
                .collect(Collectors.toList());
    }

    public boolean updateStatus(String transactionId, String newStatus, String notes) {
        MobilePaymentGrpcIngressEndpointModel model = storageCache.get(transactionId);
        if (model != null) {
            model.setOperationalStatus(newStatus);
            model.setInvestigatorNotes(notes + " [Audited at " + LocalDateTime.now() + "]");
            model.incrementRevision();
            return true;
        }
        return false;
    }

    public boolean removeTransaction(String transactionId) {
        return storageCache.remove(transactionId) != null;
    }

    public double getAverageLatencyMs() {
        return processedCount > 0 ? (cumulativeLatencyNano / processedCount) / 1_000_000.0 : 0.0;
    }

    public double getFraudRate() {
        return processedCount > 0 ? ((double) fraudBlockedCount / processedCount) : 0.0;
    }

    public long getProcessedCount() {
        return processedCount;
    }

    public boolean isActive() {
        return isActive;
    }
}
