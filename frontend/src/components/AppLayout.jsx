import React, { useState } from 'react'
import { NavLink, useNavigate } from 'react-router-dom'
import { useAuth } from '../api/AuthContext.jsx'

const NAV_ITEMS = [
  { to: '/', label: 'Dashboard', icon: '◧' },
  { to: '/skills', label: 'Skills', icon: '◆' },
  { to: '/dsa', label: 'DSA Tracker', icon: '⟨/⟩' },
  { to: '/learning', label: 'Learning', icon: '◎' },
  { to: '/projects', label: 'Projects', icon: '▣' },
  { to: '/certifications', label: 'Certifications', icon: '✦' },
  { to: '/applications', label: 'Applications', icon: '✉' },
  { to: '/interviews', label: 'Interviews', icon: '◈' },
  { to: '/profile', label: 'Profile', icon: '●' },
]

export default function AppLayout({ children }) {
  const { user, logout } = useAuth()
  const navigate = useNavigate()
  const [sidebarOpen, setSidebarOpen] = useState(false)

  const handleLogout = () => {
    logout()
    navigate('/login')
  }

  const initials = (user?.fullName || '?')
    .split(' ')
    .map((p) => p[0])
    .slice(0, 2)
    .join('')
    .toUpperCase()

  return (
    <div className="app-shell">
      <button className="mobile-toggle" onClick={() => setSidebarOpen((s) => !s)}>☰</button>

      <aside className={`sidebar ${sidebarOpen ? 'open' : ''}`}>
        <div className="sidebar-brand">
          <div className="brand-mark">DC</div>
          <div>
            <div className="brand-title">DevCareer</div>
            <div className="brand-sub">OS</div>
          </div>
        </div>

        <nav className="sidebar-nav">
          {NAV_ITEMS.map((item) => (
            <NavLink
              key={item.to}
              to={item.to}
              end={item.to === '/'}
              className={({ isActive }) => `nav-item ${isActive ? 'active' : ''}`}
              onClick={() => setSidebarOpen(false)}
            >
              <span className="nav-icon">{item.icon}</span>
              <span>{item.label}</span>
            </NavLink>
          ))}
        </nav>

        <div className="sidebar-footer">
          <div className="user-chip">
            <div className="avatar">{initials}</div>
            <div className="user-chip-info">
              <div className="user-chip-name">{user?.fullName}</div>
              <div className="user-chip-email">{user?.email}</div>
            </div>
          </div>
          <button className="logout-btn" onClick={handleLogout}>Log out</button>
        </div>
      </aside>

      <main className="main-content">
        {children}
      </main>
    </div>
  )
}
