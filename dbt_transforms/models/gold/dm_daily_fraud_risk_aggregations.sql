-- Gold Layer: AML Compliance & Merchant Risk Analytics Datamart
{{ config(materialized='table', schema='gold') }}

SELECT
    toDate(ingestion_timestamp) AS transaction_date,
    merchant_category_code,
    regulatory_risk_band,
    count(transaction_id) AS total_transaction_count,
    sum(transaction_amount_usd) AS total_volume_usd,
    avg(preliminary_risk_score) AS mean_risk_score,
    countIf(stream_decision = 'BLOCKED') AS total_blocked_fraud_count,
    sumIf(transaction_amount_usd, stream_decision = 'BLOCKED') AS total_intercepted_fraud_usd
FROM {{ ref('fct_cleaned_transactions') }}
GROUP BY
    toDate(ingestion_timestamp),
    merchant_category_code,
    regulatory_risk_band
ORDER BY
    transaction_date DESC,
    total_volume_usd DESC;
