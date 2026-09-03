-- Silver Layer: Deduplicated, Enriched & PII Masked Transactions
{{ config(materialized='incremental', unique_key='transaction_id', schema='silver') }}

WITH deduplicated AS (
    SELECT
        transaction_id,
        card_token,
        amount_raw,
        currency_code,
        merchant_category_code,
        ingress_latitude,
        ingress_longitude,
        event_epoch_timestamp,
        preliminary_risk_score,
        stream_decision,
        ingestion_timestamp,
        ROW_NUMBER() OVER (PARTITION BY transaction_id ORDER BY event_epoch_timestamp DESC) AS rn
    FROM {{ ref('stg_raw_card_transactions') }}
),
masked_enriched AS (
    SELECT
        d.transaction_id,
        -- Format Preserving Tokenization
        concat('TOKEN-', substring(d.card_token, 1, 8), '-XXXX') AS masked_card_token,
        d.amount_raw AS transaction_amount_usd,
        d.currency_code,
        d.merchant_category_code,
        m.is_high_risk_flag AS is_high_risk_merchant,
        d.ingress_latitude,
        d.ingress_longitude,
        d.event_epoch_timestamp,
        d.preliminary_risk_score,
        d.stream_decision,
        CASE
            WHEN d.preliminary_risk_score >= 0.85 THEN 'HIGH_FRAUD_RISK_BLOCK'
            WHEN d.preliminary_risk_score >= 0.60 THEN 'MEDIUM_RISK_REVIEW'
            ELSE 'NORMAL_CLEARED'
        END AS regulatory_risk_band,
        d.ingestion_timestamp
    FROM deduplicated d
    LEFT JOIN {{ ref('stg_raw_merchant_profiles') }} m
        ON d.merchant_category_code = m.primary_mcc
    WHERE d.rn = 1
)
SELECT * FROM masked_enriched;
