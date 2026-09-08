# Real-Time Financial Fraud High-Throughput Transaction Pipeline

Distributed Apache Flink, Kafka, and Redis Vector Store transaction anomaly and graph syndicate detection engine.

---

## 🏛️ Architecture Overview

The system is architected as an enterprise-grade, high-throughput distributed platform designed for horizontal scalability, sub-millisecond response latencies, and high fault tolerance.

```
+-----------------------------------------------------------------------------------+
|                            INGESTION & GATEWAY LAYER                              |
|   [API Gateway / Fastify]  -->  [Rate Limiter]  -->  [Authentication & AuthZ]     |
+-----------------------------------------------------------------------------------+
                                       |
                                       v
+-----------------------------------------------------------------------------------+
|                        STREAM & CORE PROCESSING ENGINE                            |
|   [Event Bus / Message Queue]  <-->  [Distributed Workers]  <-->  [Cache Store]   |
+-----------------------------------------------------------------------------------+
                                       |
                                       v
+-----------------------------------------------------------------------------------+
|                       DATA PERSISTENCE & STORAGE TIER                             |
|   [ACID Transaction Ledger]  -->  [Audit Logging Vault]  -->  [Analytics Marts]   |
+-----------------------------------------------------------------------------------+
```

---

## 📋 Prerequisites & Dependencies

- **Runtime**: Python 3.11+, Java 17+, Node.js 18+
- **Containerization**: Docker Engine 24+ and Docker Compose v2
- **Build Utilities**: Make, Apache Maven, npm

---

## ⚙️ Installation

```bash
git clone <repository_url>
cd <repository_folder>

# Python Virtual Environment
python -m venv .venv
source .venv/bin/activate  # On Windows: .venv\Scripts\activate
pip install --upgrade pip
pip install -r requirements.txt

# Node.js Dependencies
npm install
```

---

## 🔨 Build

```bash
# Build using Makefile
make build

# Or build container image
docker build -t app:latest .
```

---

## 🚀 Run

```bash
# Start backend application
python main.py

# Or start Node.js server
npm start

# Run full container stack
docker-compose up -d
```

---

## 🧪 Testing & Code Coverage

```bash
pytest --cov=. --cov-report=term-missing tests/
npm test
```

---

## 🔒 Security & Compliance

- **Zero Hardcoded Secrets**: Strictly uses environment variables (see `example.env`).
- **No Open-Source License Encumbrance**: Proprietary commercial software asset.
- **Audit Logging**: Immutable hash-chained audit trails.
