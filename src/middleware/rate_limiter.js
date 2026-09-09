/**
 * Multi-Tenant Sliding Window Rate Limiter for Real-Time-Financial-Fraud-High-Throughput-Transaction-Pipeline
 */
class SlidingWindowRateLimiter {
    constructor({ windowMs = 60000, maxRequests = 100 } = {}) {
        this.windowMs = windowMs;
        this.maxRequests = maxRequests;
        this.clients = new Map();
    }

    check(tenantKey) {
        const now = Date.now();
        const windowStart = now - this.windowMs;
        let timestamps = this.clients.get(tenantKey) || [];
        timestamps = timestamps.filter(ts => ts > windowStart);
        const allowed = timestamps.length < this.maxRequests;
        if (allowed) {
            timestamps.push(now);
            this.clients.set(tenantKey, timestamps);
        }
        return { allowed, limit: this.maxRequests, remaining: Math.max(0, this.maxRequests - timestamps.length) };
    }
}

module.exports = { SlidingWindowRateLimiter };
