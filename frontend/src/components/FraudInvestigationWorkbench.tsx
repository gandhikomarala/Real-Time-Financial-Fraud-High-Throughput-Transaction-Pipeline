import React, { useState } from 'react';

export const FraudInvestigationWorkbench: React.FC = () => {
  const [selectedCase, setSelectedCase] = useState<string | null>(null);

  return (
    <div className="p-6 bg-slate-900 border border-slate-800 rounded-xl text-white">
      <h2 className="text-lg font-bold">Fraud Investigator Quarantine Desk</h2>
      <p className="text-xs text-slate-400">Manual review console for borderline transactions (Risk Score 0.60 - 0.84)</p>
    </div>
  );
};
