# Multi-stage Dockerfile for Real-Time Financial Fraud High-Throughput Transaction Pipeline
FROM maven:3.9-eclipse-temurin-17 AS java-builder
WORKDIR /app
COPY pom.xml .
COPY ingestion/src ./ingestion/src
COPY streaming/src ./streaming/src
COPY settlement/src ./settlement/src
COPY security/src ./security/src
RUN mvn clean package -DskipTests

FROM python:3.11-slim AS runtime
WORKDIR /app

ENV PYTHONDONTWRITEBYTECODE=1 \
    PYTHONUNBUFFERED=1 \
    PORT=8000

RUN apt-get update && apt-get install -y --no-install-recommends \
    curl \
    openjdk-17-jre-headless \
    && rm -rf /var/lib/apt/lists/*

COPY requirements.txt .
RUN pip install --no-cache-dir -r requirements.txt

COPY . .
COPY --from=java-builder /app/target/*.jar /app/bin/ || true

EXPOSE 8000 3000 8081

HEALTHCHECK --interval=30s --timeout=10s --retries=3 \
    CMD curl -f http://localhost:8000/health || exit 1

ENTRYPOINT ["python", "main.py"]
