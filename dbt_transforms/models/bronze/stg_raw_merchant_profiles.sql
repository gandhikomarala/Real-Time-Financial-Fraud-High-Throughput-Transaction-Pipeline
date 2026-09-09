-- Bronze Layer: Raw Merchant Profiles & Terminal Master
{{ config(materialized='table', schema='bronze') }}

SELECT
    merchant_id,
    merchant_name,
    legal_entity_identifier,
    country_iso_code,
    acquirer_bank_id,
    primary_mcc,
    is_high_risk_flag,
    created_at
FROM {{ source('core_banking', 'merchant_master_table') }};
