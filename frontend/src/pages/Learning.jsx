import React, { useEffect, useState } from 'react'
import api from '../api/axios.js'
import { PageHeader, ErrorBanner, EmptyState, StatusBadge } from '../components/Common.jsx'
import ProgressBar from '../components/ProgressBar.jsx'
import Modal from '../components/Modal.jsx'

const emptyForm = { topic: '', category: '', progressPercentage: 0, status: 'NOT_STARTED', notes: '' }

export default function Learning() {
  const [topics, setTopics] = useState([])
  const [error, setError] = useState('')
  const [loading, setLoading] = useState(true)
  const [showModal, setShowModal] = useState(false)
  const [editing, setEditing] = useState(null)
  const [form, setForm] = useState(emptyForm)

  const load = () => {
    setLoading(true)
    api.get('/learning')
      .then((res) => setTopics(res.data))
      .catch(() => setError('Could not load learning topics.'))
      .finally(() => setLoading(false))
  }

  useEffect(load, [])

  const openCreate = () => { setEditing(null); setForm(emptyForm); setShowModal(true) }
  const openEdit = (t) => { setEditing(t); setForm(t); setShowModal(true) }

  const handleSubmit = async (e) => {
    e.preventDefault()
    const payload = { ...form, progressPercentage: Number(form.progressPercentage) }
    try {
      if (editing) await api.put(`/learning/${editing.id}`, payload)
      else await api.post('/learning', payload)
      setShowModal(false)
      load()
    } catch {
      setError('Could not save topic.')
    }
  }

  const handleDelete = async (id) => {
    if (!window.confirm('Delete this topic?')) return
    await api.delete(`/learning/${id}`)
    load()
  }

  return (
    <div>
      <PageHeader
        title="Learning Progress"
        subtitle="Track the technologies and concepts you're studying."
        action={<button className="btn-primary" onClick={openCreate}>+ Add Topic</button>}
      />
      <ErrorBanner message={error} />

      {loading ? (
        <div className="loading-screen">Loading…</div>
      ) : topics.length === 0 ? (
        <EmptyState text="No learning topics yet. Add Java, Spring Boot, React, etc." />
      ) : (
        <div className="card-grid">
          {topics.map((t) => (
            <div className="item-card" key={t.id}>
              <div className="item-card-top">
                <div>
                  <div className="item-card-title">{t.topic}</div>
                  <div className="item-card-meta">{t.category}</div>
                </div>
                <div className="item-card-actions">
                  <button onClick={() => openEdit(t)}>✎</button>
                  <button onClick={() => handleDelete(t.id)}>🗑</button>
                </div>
              </div>
              <StatusBadge status={t.status} />
              <ProgressBar value={t.progressPercentage} />
            </div>
          ))}
        </div>
      )}

      {showModal && (
        <Modal title={editing ? 'Edit Topic' : 'Add Topic'} onClose={() => setShowModal(false)}>
          <form onSubmit={handleSubmit} className="form-grid">
            <label>Topic</label>
            <input required value={form.topic} onChange={(e) => setForm({ ...form, topic: e.target.value })} placeholder="e.g. Spring Security" />

            <label>Category</label>
            <input value={form.category || ''} onChange={(e) => setForm({ ...form, category: e.target.value })} placeholder="e.g. Backend" />

            <label>Status</label>
            <select value={form.status} onChange={(e) => setForm({ ...form, status: e.target.value })}>
              <option value="NOT_STARTED">Not Started</option>
              <option value="IN_PROGRESS">In Progress</option>
              <option value="COMPLETED">Completed</option>
            </select>

            <label>Progress ({form.progressPercentage || 0}%)</label>
            <input type="range" min="0" max="100" value={form.progressPercentage || 0}
                   onChange={(e) => setForm({ ...form, progressPercentage: e.target.value })} />

            <label>Notes</label>
            <textarea value={form.notes || ''} onChange={(e) => setForm({ ...form, notes: e.target.value })} rows={3} />

            <button type="submit" className="btn-primary full">Save</button>
          </form>
        </Modal>
      )}
    </div>
  )
}
