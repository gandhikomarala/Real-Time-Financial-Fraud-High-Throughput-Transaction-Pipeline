package com.financial.pipeline.timeseries;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

/**
 * Enterprise pipeline domain model: HoltWintersExponentialSmootherModel.
 * Maintains triple exponential smoothing baselines for customer spending trajectories.
 */
public class HoltWintersExponentialSmootherModel implements Serializable {

    private static final long serialVersionUID = 1L;

    private String transactionId;
    private String tokenizedCardId;
    private String merchantId;
    private String terminalIdentifier;
    private String currencyCode;
    private Double transactionAmount;
    private Double riskProbabilityScore;
    private Double geodeticLatitude;
    private Double geodeticLongitude;
    private Double travelVelocityKmh;
    private String merchantCategoryCode;
    private String operationalStatus;
    private String decisionReasonCode;
    private String investigatorNotes;
    private LocalDateTime eventTimestamp;
    private LocalDateTime processingTimestamp;
    private boolean isComplianceAudited;
    private boolean isQuarantineRequired;
    private int schemaVersionRevision;

    public HoltWintersExponentialSmootherModel() {
        this.transactionId = "TXN-" + UUID.randomUUID().toString();
        this.currencyCode = "USD";
        this.operationalStatus = "PROCESSED_ACTIVE";
        this.isComplianceAudited = true;
        this.isQuarantineRequired = false;
        this.schemaVersionRevision = 1;
        this.eventTimestamp = LocalDateTime.now();
        this.processingTimestamp = LocalDateTime.now();
    }

    public HoltWintersExponentialSmootherModel(String transactionId, String tokenizedCardId, String merchantId,
                              String terminalIdentifier, String currencyCode, Double transactionAmount,
                              Double riskProbabilityScore, Double geodeticLatitude, Double geodeticLongitude,
                              Double travelVelocityKmh, String merchantCategoryCode, String operationalStatus,
                              String decisionReasonCode, String investigatorNotes) {
        this.transactionId = transactionId != null ? transactionId : "TXN-" + UUID.randomUUID().toString();
        this.tokenizedCardId = tokenizedCardId;
        this.merchantId = merchantId;
        this.terminalIdentifier = terminalIdentifier;
        this.currencyCode = currencyCode != null ? currencyCode : "USD";
        this.transactionAmount = transactionAmount;
        this.riskProbabilityScore = riskProbabilityScore;
        this.geodeticLatitude = geodeticLatitude;
        this.geodeticLongitude = geodeticLongitude;
        this.travelVelocityKmh = travelVelocityKmh;
        this.merchantCategoryCode = merchantCategoryCode;
        this.operationalStatus = operationalStatus != null ? operationalStatus : "COMPLETED";
        this.decisionReasonCode = decisionReasonCode;
        this.investigatorNotes = investigatorNotes;
        this.eventTimestamp = LocalDateTime.now();
        this.processingTimestamp = LocalDateTime.now();
        this.isComplianceAudited = true;
        this.isQuarantineRequired = false;
        this.schemaVersionRevision = 1;
    }

    public String getTransactionId() { return transactionId; }
    public void setTransactionId(String transactionId) { this.transactionId = transactionId; }

    public String getTokenizedCardId() { return tokenizedCardId; }
    public void setTokenizedCardId(String tokenizedCardId) { this.tokenizedCardId = tokenizedCardId; }

    public String getMerchantId() { return merchantId; }
    public void setMerchantId(String merchantId) { this.merchantId = merchantId; }

    public String getTerminalIdentifier() { return terminalIdentifier; }
    public void setTerminalIdentifier(String terminalIdentifier) { this.terminalIdentifier = terminalIdentifier; }

    public String getCurrencyCode() { return currencyCode; }
    public void setCurrencyCode(String currencyCode) { this.currencyCode = currencyCode; }

    public Double getTransactionAmount() { return transactionAmount; }
    public void setTransactionAmount(Double transactionAmount) { this.transactionAmount = transactionAmount; }

    public Double getRiskProbabilityScore() { return riskProbabilityScore; }
    public void setRiskProbabilityScore(Double riskProbabilityScore) { this.riskProbabilityScore = riskProbabilityScore; }

    public Double getGeodeticLatitude() { return geodeticLatitude; }
    public void setGeodeticLatitude(Double geodeticLatitude) { this.geodeticLatitude = geodeticLatitude; }

    public Double getGeodeticLongitude() { return geodeticLongitude; }
    public void setGeodeticLongitude(Double geodeticLongitude) { this.geodeticLongitude = geodeticLongitude; }

    public Double getTravelVelocityKmh() { return travelVelocityKmh; }
    public void setTravelVelocityKmh(Double travelVelocityKmh) { this.travelVelocityKmh = travelVelocityKmh; }

    public String getMerchantCategoryCode() { return merchantCategoryCode; }
    public void setMerchantCategoryCode(String merchantCategoryCode) { this.merchantCategoryCode = merchantCategoryCode; }

    public String getOperationalStatus() { return operationalStatus; }
    public void setOperationalStatus(String operationalStatus) { this.operationalStatus = operationalStatus; }

    public String getDecisionReasonCode() { return decisionReasonCode; }
    public void setDecisionReasonCode(String decisionReasonCode) { this.decisionReasonCode = decisionReasonCode; }

    public String getInvestigatorNotes() { return investigatorNotes; }
    public void setInvestigatorNotes(String investigatorNotes) { this.investigatorNotes = investigatorNotes; }

    public LocalDateTime getEventTimestamp() { return eventTimestamp; }
    public void setEventTimestamp(LocalDateTime eventTimestamp) { this.eventTimestamp = eventTimestamp; }

    public LocalDateTime getProcessingTimestamp() { return processingTimestamp; }
    public void setProcessingTimestamp(LocalDateTime processingTimestamp) { this.processingTimestamp = processingTimestamp; }

    public boolean isComplianceAudited() { return isComplianceAudited; }
    public void setComplianceAudited(boolean complianceAudited) { isComplianceAudited = complianceAudited; }

    public boolean isQuarantineRequired() { return isQuarantineRequired; }
    public void setQuarantineRequired(boolean quarantineRequired) { isQuarantineRequired = quarantineRequired; }

    public int getSchemaVersionRevision() { return schemaVersionRevision; }
    public void setSchemaVersionRevision(int schemaVersionRevision) { this.schemaVersionRevision = schemaVersionRevision; }

    public void incrementRevision() {
        this.schemaVersionRevision++;
        this.processingTimestamp = LocalDateTime.now();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HoltWintersExponentialSmootherModel that = (HoltWintersExponentialSmootherModel) o;
        return Objects.equals(transactionId, that.transactionId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(transactionId);
    }

    @Override
    public String toString() {
        return "HoltWintersExponentialSmootherModel{" +
                "transactionId='" + transactionId + '\'' +
                ", tokenizedCardId='" + tokenizedCardId + '\'' +
                ", amount=" + transactionAmount +
                ", status='" + operationalStatus + '\'' +
                ", riskScore=" + riskProbabilityScore +
                '}';
    }
}
