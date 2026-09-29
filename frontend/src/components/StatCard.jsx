import React from 'react'

export default function StatCard({ label, value, accent = 'violet', suffix = '' }) {
  return (
    <div className={`stat-card accent-${accent}`}>
      <div className="stat-value">{value}{suffix}</div>
      <div className="stat-label">{label}</div>
    </div>
  )
}
