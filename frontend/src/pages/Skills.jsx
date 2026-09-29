import React, { useEffect, useState } from 'react'
import api from '../api/axios.js'
import { PageHeader, ErrorBanner, EmptyState } from '../components/Common.jsx'
import ProgressBar from '../components/ProgressBar.jsx'
import Modal from '../components/Modal.jsx'

const emptyForm = { name: '', category: '', level: 'Beginner', progressPercentage: 0 }

export default function Skills() {
  const [skills, setSkills] = useState([])
  const [error, setError] = useState('')
  const [loading, setLoading] = useState(true)
  const [showModal, setShowModal] = useState(false)
  const [editing, setEditing] = useState(null)
  const [form, setForm] = useState(emptyForm)

  const load = () => {
    setLoading(true)
    api.get('/skills')
      .then((res) => setSkills(res.data))
      .catch(() => setError('Could not load skills.'))
      .finally(() => setLoading(false))
  }

  useEffect(load, [])

  const openCreate = () => {
    setEditing(null)
    setForm(emptyForm)
    setShowModal(true)
  }

  const openEdit = (skill) => {
    setEditing(skill)
    setForm(skill)
    setShowModal(true)
  }

  const handleSubmit = async (e) => {
    e.preventDefault()
    const payload = { ...form, progressPercentage: Number(form.progressPercentage) }
    try {
      if (editing) {
        await api.put(`/skills/${editing.id}`, payload)
      } else {
        await api.post('/skills', payload)
      }
      setShowModal(false)
      load()
    } catch {
      setError('Could not save skill.')
    }
  }

  const handleDelete = async (id) => {
    if (!window.confirm('Delete this skill?')) return
    await api.delete(`/skills/${id}`)
    load()
  }

  return (
    <div>
      <PageHeader
        title="Skills"
        subtitle="Track how confident you are in each technology."
        action={<button className="btn-primary" onClick={openCreate}>+ Add Skill</button>}
      />
      <ErrorBanner message={error} />

      {loading ? (
        <div className="loading-screen">Loading…</div>
      ) : skills.length === 0 ? (
        <EmptyState text="No skills added yet. Click “Add Skill” to get started." />
      ) : (
        <div className="card-grid">
          {skills.map((s) => (
            <div className="item-card" key={s.id}>
              <div className="item-card-top">
                <div>
                  <div className="item-card-title">{s.name}</div>
                  <div className="item-card-meta">{s.category} · {s.level}</div>
                </div>
                <div className="item-card-actions">
                  <button onClick={() => openEdit(s)}>✎</button>
                  <button onClick={() => handleDelete(s.id)}>🗑</button>
                </div>
              </div>
              <ProgressBar value={s.progressPercentage} />
            </div>
          ))}
        </div>
      )}

      {showModal && (
        <Modal title={editing ? 'Edit Skill' : 'Add Skill'} onClose={() => setShowModal(false)}>
          <form onSubmit={handleSubmit} className="form-grid">
            <label>Skill name</label>
            <input required value={form.name} onChange={(e) => setForm({ ...form, name: e.target.value })} placeholder="e.g. Java" />

            <label>Category</label>
            <input value={form.category || ''} onChange={(e) => setForm({ ...form, category: e.target.value })} placeholder="e.g. Backend" />

            <label>Level</label>
            <select value={form.level || 'Beginner'} onChange={(e) => setForm({ ...form, level: e.target.value })}>
              <option>Beginner</option>
              <option>Intermediate</option>
              <option>Advanced</option>
            </select>

            <label>Progress ({form.progressPercentage || 0}%)</label>
            <input type="range" min="0" max="100" value={form.progressPercentage || 0}
                   onChange={(e) => setForm({ ...form, progressPercentage: e.target.value })} />

            <button type="submit" className="btn-primary full">Save</button>
          </form>
        </Modal>
      )}
    </div>
  )
}
