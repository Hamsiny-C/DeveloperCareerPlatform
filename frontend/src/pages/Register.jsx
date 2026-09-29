import React, { useState } from 'react'
import { Link, useNavigate } from 'react-router-dom'
import { useAuth } from '../api/AuthContext.jsx'

export default function Register() {
  const { register } = useAuth()
  const navigate = useNavigate()
  const [fullName, setFullName] = useState('')
  const [email, setEmail] = useState('')
  const [password, setPassword] = useState('')
  const [error, setError] = useState('')
  const [loading, setLoading] = useState(false)

  const handleSubmit = async (e) => {
    e.preventDefault()
    setError('')
    setLoading(true)
    try {
      await register(fullName, email, password)
      navigate('/')
    } catch (err) {
      const data = err.response?.data
      const msg = data?.message || (data && Object.values(data)[0]) || 'Registration failed.'
      setError(msg)
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
          <p>Create your account and start building a structured, trackable placement preparation plan.</p>
          <ul className="auth-highlights">
            <li>One dashboard for your whole prep journey</li>
            <li>Built for Java Full Stack developers</li>
            <li>Runs fully on your own machine</li>
          </ul>
        </div>
      </div>

      <div className="auth-form-side">
        <form className="auth-card" onSubmit={handleSubmit}>
          <h1>Create your account</h1>
          <p className="auth-sub">It only takes a minute.</p>

          {error && <div className="error-banner">{error}</div>}

          <label>Full name</label>
          <input type="text" value={fullName} required
                 onChange={(e) => setFullName(e.target.value)}
                 placeholder="Jane Doe" />

          <label>Email</label>
          <input type="email" value={email} required
                 onChange={(e) => setEmail(e.target.value)}
                 placeholder="you@example.com" />

          <label>Password</label>
          <input type="password" value={password} required minLength={6}
                 onChange={(e) => setPassword(e.target.value)}
                 placeholder="At least 6 characters" />

          <button type="submit" className="btn-primary full" disabled={loading}>
            {loading ? 'Creating account…' : 'Create Account'}
          </button>

          <p className="auth-footer">
            Already have an account? <Link to="/login">Log in</Link>
          </p>
        </form>
      </div>
    </div>
  )
}
