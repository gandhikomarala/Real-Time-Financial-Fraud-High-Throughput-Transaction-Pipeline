-- Bronze Layer: Raw Ingested Payment Card Transactions Stream
{{ config(materialized='incremental', unique_key='transaction_id', schema='bronze') }}

WITH source_stream AS (
    SELECT
        CAST(JSONExtractString(raw_payload, 'txn_id') AS String) AS transaction_id,
        CAST(JSONExtractString(raw_payload, 'card_token') AS String) AS card_token,
        CAST(JSONExtractFloat(raw_payload, 'amount') AS Float64) AS amount_raw,
        CAST(JSONExtractString(raw_payload, 'currency') AS String) AS currency_code,
        CAST(JSONExtractString(raw_payload, 'mcc') AS String) AS merchant_category_code,
        CAST(JSONExtractFloat(raw_payload, 'lat') AS Float64) AS ingress_latitude,
        CAST(JSONExtractFloat(raw_payload, 'lon') AS Float64) AS ingress_longitude,
        CAST(JSONExtractInt(raw_payload, 'timestamp') AS Int64) AS event_epoch_timestamp,
        CAST(JSONExtractFloat(raw_payload, 'risk_score') AS Float64) AS preliminary_risk_score,
        CAST(JSONExtractString(raw_payload, 'decision') AS String) AS stream_decision,
        toDateTime(now()) AS ingestion_timestamp
    FROM {{ source('kafka_ingress', 'raw_transaction_events') }}
)
SELECT * FROM source_stream;
