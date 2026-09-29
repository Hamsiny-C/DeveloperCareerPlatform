import React, { useState } from 'react'
import { Link, useNavigate } from 'react-router-dom'
import { useAuth } from '../api/AuthContext.jsx'

export default function Login() {
  const { login } = useAuth()
  const navigate = useNavigate()
  const [email, setEmail] = useState('')
  const [password, setPassword] = useState('')
  const [error, setError] = useState('')
  const [loading, setLoading] = useState(false)

  const handleSubmit = async (e) => {
    e.preventDefault()
    setError('')
    setLoading(true)
    try {
      await login(email, password)
      navigate('/')
    } catch (err) {
      setError(err.response?.data?.message || 'Invalid email or password.')
    } finally {
      setLoading(false)
    }
  }

  return (
    <div className="auth-screen">
      <div className="auth-visual">
        <div className="auth-visual-inner">
          <div className="brand-mark large">DC</div>
          <h2>DevCareer OS</h2>
          <p>Your personal developer career operating system — skills, DSA, projects, applications and interviews, all in one place.</p>
          <ul className="auth-highlights">
            <li>Track DSA progress topic by topic</li>
            <li>Manage job applications end-to-end</li>
            <li>See your career readiness at a glance</li>
          </ul>
        </div>
      </div>

      <div className="auth-form-side">
        <form className="auth-card" onSubmit={handleSubmit}>
          <h1>Welcome back</h1>
          <p className="auth-sub">Log in to continue your prep journey.</p>

          {error && <div className="error-banner">{error}</div>}

          <label>Email</label>
          <input type="email" value={email} required
                 onChange={(e) => setEmail(e.target.value)}
                 placeholder="you@example.com" />

          <label>Password</label>
          <input type="password" value={password} required
                 onChange={(e) => setPassword(e.target.value)}
                 placeholder="••••••••" />

          <button type="submit" className="btn-primary full" disabled={loading}>
            {loading ? 'Logging in…' : 'Log In'}
          </button>

          <p className="auth-footer">
            Don't have an account? <Link to="/register">Create one</Link>
          </p>
        </form>
      </div>
    </div>
  )
}
