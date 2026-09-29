import React, { useEffect, useState } from 'react'
import api from '../api/axios.js'
import { PageHeader, ErrorBanner, EmptyState, StatusBadge } from '../components/Common.jsx'
import Modal from '../components/Modal.jsx'

const emptyForm = {
  name: '', description: '', technologies: '', githubUrl: '', liveUrl: '',
  status: 'IN_PROGRESS', startDate: '', endDate: '',
}

export default function Projects() {
  const [projects, setProjects] = useState([])
  const [error, setError] = useState('')
  const [loading, setLoading] = useState(true)
  const [showModal, setShowModal] = useState(false)
  const [editing, setEditing] = useState(null)
  const [form, setForm] = useState(emptyForm)

  const load = () => {
    setLoading(true)
    api.get('/projects')
      .then((res) => setProjects(res.data))
      .catch(() => setError('Could not load projects.'))
      .finally(() => setLoading(false))
  }

  useEffect(load, [])

  const openCreate = () => { setEditing(null); setForm(emptyForm); setShowModal(true) }
  const openEdit = (p) => {
    setEditing(p)
    setForm({ ...p, startDate: p.startDate || '', endDate: p.endDate || '' })
    setShowModal(true)
  }

  const handleSubmit = async (e) => {
    e.preventDefault()
    const payload = { ...form, startDate: form.startDate || null, endDate: form.endDate || null }
    try {
      if (editing) await api.put(`/projects/${editing.id}`, payload)
      else await api.post('/projects', payload)
      setShowModal(false)
      load()
    } catch {
      setError('Could not save project.')
    }
  }

  const handleDelete = async (id) => {
    if (!window.confirm('Delete this project?')) return
    await api.delete(`/projects/${id}`)
    load()
  }

  return (
    <div>
      <PageHeader
        title="Projects"
        subtitle="Everything you've built, in one portfolio."
        action={<button className="btn-primary" onClick={openCreate}>+ Add Project</button>}
      />
      <ErrorBanner message={error} />

      {loading ? (
        <div className="loading-screen">Loading…</div>
      ) : projects.length === 0 ? (
        <EmptyState text="No projects added yet. Add your first one." />
      ) : (
        <div className="card-grid">
          {projects.map((p) => (
            <div className="item-card project-card" key={p.id}>
              <div className="item-card-top">
                <div>
                  <div className="item-card-title">{p.name}</div>
                  <StatusBadge status={p.status} />
                </div>
                <div className="item-card-actions">
                  <button onClick={() => openEdit(p)}>✎</button>
                  <button onClick={() => handleDelete(p.id)}>🗑</button>
                </div>
              </div>
              {p.description && <p className="item-card-desc">{p.description}</p>}
              {p.technologies && (
                <div className="tech-chips">
                  {p.technologies.split(',').map((t) => t.trim()).filter(Boolean).map((t) => (
                    <span key={t} className="tech-chip">{t}</span>
                  ))}
                </div>
              )}
              <div className="project-links">
                {p.githubUrl && <a href={p.githubUrl} target="_blank" rel="noreferrer">GitHub ↗</a>}
                {p.liveUrl && <a href={p.liveUrl} target="_blank" rel="noreferrer">Live ↗</a>}
              </div>
            </div>
          ))}
        </div>
      )}

      {showModal && (
        <Modal title={editing ? 'Edit Project' : 'Add Project'} onClose={() => setShowModal(false)}>
          <form onSubmit={handleSubmit} className="form-grid">
            <label>Project name</label>
            <input required value={form.name} onChange={(e) => setForm({ ...form, name: e.target.value })} placeholder="e.g. DevCareer OS" />

            <label>Description</label>
            <textarea value={form.description || ''} onChange={(e) => setForm({ ...form, description: e.target.value })} rows={3} />

            <label>Technologies (comma separated)</label>
            <input value={form.technologies || ''} onChange={(e) => setForm({ ...form, technologies: e.target.value })} placeholder="Java, Spring Boot, React" />

            <label>GitHub URL</label>
            <input value={form.githubUrl || ''} onChange={(e) => setForm({ ...form, githubUrl: e.target.value })} placeholder="https://github.com/..." />

            <label>Live URL</label>
            <input value={form.liveUrl || ''} onChange={(e) => setForm({ ...form, liveUrl: e.target.value })} placeholder="https://..." />

            <label>Status</label>
            <select value={form.status} onChange={(e) => setForm({ ...form, status: e.target.value })}>
              <option value="PLANNED">Planned</option>
              <option value="IN_PROGRESS">In Progress</option>
              <option value="COMPLETED">Completed</option>
            </select>

            <label>Start date</label>
            <input type="date" value={form.startDate || ''} onChange={(e) => setForm({ ...form, startDate: e.target.value })} />

            <label>End date</label>
            <input type="date" value={form.endDate || ''} onChange={(e) => setForm({ ...form, endDate: e.target.value })} />

            <button type="submit" className="btn-primary full">Save</button>
          </form>
        </Modal>
      )}
    </div>
  )
}
