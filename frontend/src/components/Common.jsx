import React from 'react'

export function StatusBadge({ status }) {
  const key = (status || '').toLowerCase().replace(/_/g, '-')
  return <span className={`badge badge-${key}`}>{(status || '').replace(/_/g, ' ')}</span>
}

export function PageHeader({ title, subtitle, action }) {
  return (
    <div className="page-header">
      <div>
        <h1>{title}</h1>
        {subtitle && <p>{subtitle}</p>}
      </div>
      {action && <div>{action}</div>}
    </div>
  )
}

export function EmptyState({ text }) {
  return <div className="empty-state">{text}</div>
}

export function ErrorBanner({ message }) {
  if (!message) return null
  return <div className="error-banner">{message}</div>
}
