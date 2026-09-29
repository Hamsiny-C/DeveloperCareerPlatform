import React, { useEffect, useState } from 'react'
import api from '../api/axios.js'
import { useAuth } from '../api/AuthContext.jsx'
import { PageHeader, ErrorBanner } from '../components/Common.jsx'
import StatCard from '../components/StatCard.jsx'
import ProgressBar from '../components/ProgressBar.jsx'

export default function Dashboard() {
  const { user } = useAuth()
  const [summary, setSummary] = useState(null)
  const [error, setError] = useState('')
  const [loading, setLoading] = useState(true)

  useEffect(() => {
    api.get('/dashboard/summary')
      .then((res) => setSummary(res.data))
      .catch(() => setError('Could not load dashboard data.'))
      .finally(() => setLoading(false))
  }, [])

  if (loading) return <div className="loading-screen">Loading your dashboard…</div>

  const skillEntries = summary ? Object.entries(summary.skillProgressMap || {}) : []

  return (
    <div>
      <PageHeader
        title={`Welcome back, ${user?.fullName?.split(' ')[0] || 'there'} 👋`}
        subtitle="Here's where your placement preparation stands today."
      />
      <ErrorBanner message={error} />

      {summary && (
        <>
          <div className="career-progress-card">
            <div>
              <div className="career-progress-label">Overall Career Readiness</div>
              <div className="career-progress-value">{summary.careerProgressPercentage}%</div>
            </div>
            <ProgressBar value={summary.careerProgressPercentage} showValue={false} />
          </div>

          <div className="stat-grid">
            <StatCard label="DSA Solved" value={summary.dsaSolved} accent="violet" />
            <StatCard label="Total DSA Problems" value={summary.totalDsaProblems} accent="blue" />
            <StatCard label="Projects" value={summary.totalProjects} accent="teal" />
            <StatCard label="Certifications" value={summary.totalCertifications} accent="amber" />
            <StatCard label="Applications" value={summary.totalApplications} accent="pink" />
            <StatCard label="Upcoming Interviews" value={summary.upcomingInterviews} accent="green" />
          </div>

          <div className="dashboard-grid">
            <div className="panel">
              <h3>DSA Breakdown</h3>
              <div className="dsa-breakdown">
                <div className="dsa-pill easy">Easy: {summary.dsaEasy}</div>
                <div className="dsa-pill medium">Medium: {summary.dsaMedium}</div>
                <div className="dsa-pill hard">Hard: {summary.dsaHard}</div>
              </div>
              <ProgressBar
                label="Learning Progress (avg)"
                value={summary.averageLearningProgress}
              />
              <ProgressBar
                label="Skills Progress (avg)"
                value={summary.averageSkillProgress}
              />
            </div>

            <div className="panel">
              <h3>Skill Progress</h3>
              {skillEntries.length === 0 && <p className="muted">Add some skills to see progress here.</p>}
              {skillEntries.map(([name, value]) => (
                <ProgressBar key={name} label={name} value={value} />
              ))}
            </div>
          </div>

          <div className="panel">
            <h3>Recent Activity</h3>
            {(!summary.recentActivities || summary.recentActivities.length === 0) && (
              <p className="muted">No recent activity yet — start solving problems to see it here.</p>
            )}
            <ul className="activity-list">
              {summary.recentActivities?.map((item, idx) => (
                <li key={idx}>
                  <span className="activity-dot" />
                  <span>{item.description}</span>
                  <span className="activity-date">{item.date}</span>
                </li>
              ))}
            </ul>
          </div>
        </>
      )}
    </div>
  )
}
