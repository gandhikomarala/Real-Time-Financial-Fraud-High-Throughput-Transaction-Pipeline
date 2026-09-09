/**
 * Real-Time Financial Fraud Telemetry Server
 * Express & WebSocket server for Mission Control Dashboard
 */
const http = require('http');

const PORT = process.env.PORT || 3000;

const server = http.createServer((req, res) => {
    if (req.url === '/health' || req.url === '/') {
        res.writeHead(200, { 'Content-Type': 'application/json' });
        res.end(JSON.stringify({
            status: 'UP',
            service: 'Fraud Mission Control Telemetry',
            version: '2.4.0',
            timestamp: new Date().toISOString()
        }));
    } else if (req.url === '/api/metrics') {
        res.writeHead(200, { 'Content-Type': 'application/json' });
        res.end(JSON.stringify({
            throughput_tps: 12450,
            p99_latency_ms: 3.42,
            anomalies_detected_today: 142,
            blocked_fraud_amount_usd: 1845200.00
        }));
    } else {
        res.writeHead(404, { 'Content-Type': 'application/json' });
        res.end(JSON.stringify({ error: 'Not Found' }));
    }
});

server.listen(PORT, () => {
    console.log(`[Fraud-Telemetry] Server listening on port ${PORT}`);
});
