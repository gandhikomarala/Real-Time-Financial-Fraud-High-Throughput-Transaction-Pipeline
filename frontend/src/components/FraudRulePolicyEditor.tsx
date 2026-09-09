import React, { useState } from 'react';

interface RuleDefinition {
  id: string;
  ruleName: string;
  condition: string;
  riskWeight: number;
  isActive: boolean;
}

export const FraudRulePolicyEditor: React.FC = () => {
  const [rules, setRules] = useState<RuleDefinition[]>([
    { id: 'R-01', ruleName: 'Haversine Impossible Travel', condition: 'travel_speed_kmh > 900.0', riskWeight: 0.55, isActive: true },
    { id: 'R-02', ruleName: 'Sliding Window Velocity Burst', condition: 'card_txns_last_60s > 5', riskWeight: 0.45, isActive: true },
    { id: 'R-03', ruleName: 'High Risk Merchant Category', condition: 'mcc IN (6051, 5944, 7995)', riskWeight: 0.25, isActive: true },
    { id: 'R-04', ruleName: 'High Amount First Time Outlier', condition: 'amount > 3000 AND card_age_days < 7', riskWeight: 0.35, isActive: true }
  ]);

  const toggleRule = (id: string) => {
    setRules(prev => prev.map(r => r.id === id ? { ...r, isActive: !r.isActive } : r));
  };

  return (
    <div className="bg-slate-900 border border-slate-800 rounded-xl p-6 text-white shadow-xl">
      <h2 className="text-lg font-bold mb-2">Complex Event Processing (CEP) Dynamic Rule Policies</h2>
      <p className="text-xs text-slate-400 mb-6 font-mono">Rules evaluated statefully across Apache Flink streaming sliding windows</p>
      <div className="overflow-x-auto">
        <table className="w-full text-left text-sm font-mono">
          <thead className="bg-slate-950 text-slate-400 uppercase text-xs">
            <tr>
              <th className="py-3 px-4">Rule ID</th>
              <th className="py-3 px-4">Policy Name</th>
              <th className="py-3 px-4">Stream Predicate Condition</th>
              <th className="py-3 px-4">Weight</th>
              <th className="py-3 px-4">State</th>
            </tr>
          </thead>
          <tbody className="divide-y divide-slate-800">
            {rules.map(rule => (
              <tr key={rule.id} className="hover:bg-slate-800/40">
                <td className="py-3 px-4 text-blue-400">{rule.id}</td>
                <td className="py-3 px-4 font-semibold text-white">{rule.ruleName}</td>
                <td className="py-3 px-4 text-slate-300"><code>{rule.condition}</code></td>
                <td className="py-3 px-4 text-amber-400 font-bold">+{rule.riskWeight}</td>
                <td className="py-3 px-4">
                  <button onClick={() => toggleRule(rule.id)} className={`px-3 py-1 rounded text-xs font-bold ${rule.isActive ? 'bg-emerald-500/20 text-emerald-400 border border-emerald-500/40' : 'bg-slate-800 text-slate-400'}`}>
                    {rule.isActive ? 'ACTIVE' : 'DISABLED'}
                  </button>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </div>
  );
};
