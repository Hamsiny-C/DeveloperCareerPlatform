import React, { useEffect, useState } from 'react'
import api from '../api/axios.js'
import { PageHeader, ErrorBanner } from '../components/Common.jsx'

export default function Profile() {
  const [profile, setProfile] = useState(null)
  const [error, setError] = useState('')
  const [saved, setSaved] = useState(false)
  const [loading, setLoading] = useState(true)

  useEffect(() => {
    api.get('/profile')
      .then((res) => setProfile(res.data))
      .catch(() => setError('Could not load profile.'))
      .finally(() => setLoading(false))
  }, [])

  const handleChange = (field, value) => setProfile({ ...profile, [field]: value })

  const handleSubmit = async (e) => {
    e.preventDefault()
    setError('')
    setSaved(false)
    try {
      const res = await api.put('/profile', profile)
      setProfile(res.data)
      setSaved(true)
      setTimeout(() => setSaved(false), 2500)
    } catch {
      setError('Could not save profile.')
    }
  }

  if (loading) return <div className="loading-screen">Loading…</div>

  return (
    <div>
      <PageHeader title="Profile" subtitle="Your account and career target details." />
      <ErrorBanner message={error} />
      {saved && <div className="success-banner">Profile updated successfully.</div>}

      {profile && (
        <form onSubmit={handleSubmit} className="panel form-grid" style={{ maxWidth: 520 }}>
          <label>Full name</label>
          <input value={profile.fullName || ''} onChange={(e) => handleChange('fullName', e.target.value)} />

          <label>Email</label>
          <input value={profile.email || ''} disabled />

          <label>Current / Target job title</label>
          <input value={profile.jobTitle || ''} onChange={(e) => handleChange('jobTitle', e.target.value)}
                 placeholder="e.g. Java Full Stack Developer" />

          <label>Target role</label>
          <input value={profile.targetRole || ''} onChange={(e) => handleChange('targetRole', e.target.value)}
                 placeholder="e.g. SDE-1" />

          <label>Bio</label>
          <textarea rows={4} value={profile.bio || ''} onChange={(e) => handleChange('bio', e.target.value)} />

          <button type="submit" className="btn-primary full">Save Changes</button>
        </form>
      )}
    </div>
  )
}
