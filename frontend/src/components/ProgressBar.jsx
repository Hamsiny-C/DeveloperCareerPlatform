import React from 'react'

export default function ProgressBar({ value = 0, label, showValue = true }) {
  const pct = Math.max(0, Math.min(100, value))
  return (
    <div className="progress-wrap">
      {label && (
        <div className="progress-label-row">
          <span>{label}</span>
          {showValue && <span className="progress-value">{pct}%</span>}
        </div>
      )}
      <div className="progress-track">
        <div className="progress-fill" style={{ width: `${pct}%` }} />
      </div>
    </div>
  )
}
