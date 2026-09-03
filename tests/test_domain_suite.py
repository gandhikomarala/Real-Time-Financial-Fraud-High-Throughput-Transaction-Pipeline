import math
import unittest

def haversine_distance_km(lat1, lon1, lat2, lon2):
    R = 6371.0  # Earth radius in kilometers
    dlat = math.radians(lat2 - lat1)
    dlon = math.radians(lon2 - lon1)
    a = (math.sin(dlat / 2) ** 2 +
         math.cos(math.radians(lat1)) * math.cos(math.radians(lat2)) *
         math.sin(dlon / 2) ** 2)
    c = 2 * math.atan2(math.sqrt(a), math.sqrt(1 - a))
    return R * c

def calculate_velocity_speed_kmh(lat1, lon1, t1_sec, lat2, lon2, t2_sec):
    time_diff_hours = abs(t2_sec - t1_sec) / 3600.0
    if time_diff_hours <= 0:
        return float('inf')
    distance = haversine_distance_km(lat1, lon1, lat2, lon2)
    return distance / time_diff_hours

def evaluate_fraud_risk(amount, speed_kmh, mcc, recent_txn_count):
    score = 0.0
    if recent_txn_count > 5:
        score += 0.40
    if speed_kmh > 900.0:
        score += 0.50
    if mcc in ['6051', '5944', '7995']:
        score += 0.20
    if amount > 3000.0:
        score += 0.30

    risk_prob = min(1.0, score)
    decision = "BLOCKED" if risk_prob >= 0.85 else ("REVIEW" if risk_prob >= 0.60 else "APPROVED")
    return risk_prob, decision

class TestFraudPipelineEngine(unittest.TestCase):

    def test_haversine_distance_precision(self):
        # NY (40.7128, -74.0060) to London (51.5074, -0.1278) ~ 5570 km
        dist = haversine_distance_km(40.7128, -74.0060, 51.5074, -0.1278)
        self.assertAlmostEqual(dist, 5570.0, delta=50.0)

    def test_impossible_travel_triggers_block(self):
        # 5570 km within 30 minutes (0.5h) = 11140 km/h -> IMPOSSIBLE TRAVEL
        speed = calculate_velocity_speed_kmh(40.7128, -74.0060, 0, 51.5074, -0.1278, 1800)
        self.assertGreater(speed, 900.0)
        score, decision = evaluate_fraud_risk(4500.0, speed, '5944', 6)
        self.assertEqual(decision, "BLOCKED")
        self.assertGreaterEqual(score, 0.85)

    def test_normal_transaction_approved(self):
        speed = calculate_velocity_speed_kmh(40.7128, -74.0060, 0, 40.7580, -73.9855, 3600)
        self.assertLess(speed, 50.0)
        score, decision = evaluate_fraud_risk(45.0, speed, '5411', 1)
        self.assertEqual(decision, "APPROVED")
        self.assertLess(score, 0.60)

    def test_mcc_risk_weighting(self):
        score, decision = evaluate_fraud_risk(1200.0, 10.0, '6051', 4)
        self.assertIn(decision, ["REVIEW", "APPROVED"])

if __name__ == '__main__':
    unittest.main()
