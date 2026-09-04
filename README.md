# Real-Time Financial Fraud High-Throughput Transaction Pipeline

A high-performance, distributed streaming analytics and financial fraud detection pipeline built for mission-critical banking, payment networks, and fintech infrastructure. Processes high-volume credit/debit card streams with sub-5ms anomaly scoring, graph-based syndicate detection, and automated regulatory reporting.

---

## 🏛️ Architecture Overview

```
+-----------------------------------------------------------------------------------+
|                           TRANSACTION INGESTION LAYER                             |
|  [ISO-8583 Gateway]  -->  [Kafka Event Stream]  -->  [Schema Registry / Avro]     |
+-----------------------------------------------------------------------------------+
                                       |
                                       v
+-----------------------------------------------------------------------------------+
|                        REAL-TIME STREAM PROCESSING LAYER                          |
|  [Apache Flink Engine]  <-->  [Redis Vector Store]  <-->  [Feature Store / Feast] |
|   - 10-sec Velocity Windows    - Sub-5ms Vector Lookup     - Historical Aggregates|
+-----------------------------------------------------------------------------------+
                                       |
                                       v
+-----------------------------------------------------------------------------------+
|                       ML INFERENCE & GRAPH INTELLIGENCE                           |
|  [Isolation Forest / XGBoost]  <-->  [PyTorch Geometric GNN]  --> [Rules Engine]   |
|   - Anomaly Probability               - Money Mule Rings           - Hard Velocity|
+-----------------------------------------------------------------------------------+
                                       |
                                       v
+-----------------------------------------------------------------------------------+
|                      SETTLEMENT, AUDIT & GOVERNANCE LAYER                         |
|  [PCI-DSS Tokenization Vault]  -->  [Delta Lake Medallion]  -->  [SOX Audit Logs] |
|  [Trino Analytics Engine]      -->  [FastAPI / WebSocket]   -->  [React 18 Ops UI]|
+-----------------------------------------------------------------------------------+
```

---

## 📋 Prerequisites & Dependencies

- **Java**: OpenJDK 17 or higher
- **Python**: Python 3.10 or 3.11
- **Node.js**: Node 18+ and npm 9+
- **Docker**: Docker Engine 24+ and Docker Compose v2
- **Build Tools**: Apache Maven 3.8+, Make

---

## ⚙️ Installation

### 1. Clone & Setup Environment
```bash
git clone git@github.com:gandhikomarala/Real-Time-Financial-Fraud-High-Throughput-Transaction-Pipeline.git
cd Real-Time-Financial-Fraud-High-Throughput-Transaction-Pipeline
```

### 2. Python Virtual Environment & Dependencies
```bash
python -m venv .venv
source .venv/bin/activate  # On Windows: .venv\Scripts\activate
pip install --upgrade pip
pip install -r requirements.txt
```

### 3. Node.js Frontend Dependencies
```bash
npm install
```

### 4. Java Maven Compilation
```bash
mvn clean compile -DskipTests
```

---

## 🔨 Build

### Using Makefile
```bash
make build
```

### Using Docker
```bash
docker build -t fraud-detection-pipeline:latest .
```

---

## 🚀 Run

### Local Development Mode
```bash
# Start backend ingestion and API services
python main.py

# Or start the Node.js / Express telemetry server
npm start
```

### Distributed Container Stack
```bash
docker-compose up -d
```

Services exposed:
- **FastAPI Telemetry Gateway**: `http://localhost:8000` (Swagger UI at `/docs`)
- **React 18 Fraud Mission Control**: `http://localhost:3000`
- **Apache Flink Dashboard**: `http://localhost:8081`
- **Prometheus Metrics**: `http://localhost:9090`
- **Grafana Dashboards**: `http://localhost:3001`

---

## 🧪 Testing & Code Coverage

Run the comprehensive domain and regression test suite:
```bash
pytest --cov=. --cov-report=term-missing tests/
```

Or using npm:
```bash
npm test
```

---

## 🔒 Security & Compliance

- **Zero Hardcoded Secrets**: Configuration is strictly managed through environment variables (see `example.env`).
- **PCI-DSS Tokenization**: Primary Account Numbers (PANs) are vaulted using AES-256-GCM encryption with HSM envelope keys.
- **SOX Section 404 Audit Logging**: Every transaction decision produces an immutable hash-chained audit receipt.
- **Proprietary Commercial Codebase**: 100% original code without open-source license encumbrances.
