import React, { useEffect, useState } from 'react'
import api from '../api/axios.js'
import { PageHeader, ErrorBanner, EmptyState, StatusBadge } from '../components/Common.jsx'
import Modal from '../components/Modal.jsx'

const emptyForm = {
  problemName: '', topic: '', difficulty: 'EASY', platform: '',
  status: 'TODO', dateSolved: '', notes: '',
}

export default function Dsa() {
  const [problems, setProblems] = useState([])
  const [error, setError] = useState('')
  const [loading, setLoading] = useState(true)
  const [showModal, setShowModal] = useState(false)
  const [editing, setEditing] = useState(null)
  const [form, setForm] = useState(emptyForm)

  const load = () => {
    setLoading(true)
    api.get('/dsa')
      .then((res) => setProblems(res.data))
      .catch(() => setError('Could not load DSA problems.'))
      .finally(() => setLoading(false))
  }

  useEffect(load, [])

  const openCreate = () => { setEditing(null); setForm(emptyForm); setShowModal(true) }
  const openEdit = (p) => { setEditing(p); setForm({ ...p, dateSolved: p.dateSolved || '' }); setShowModal(true) }

  const handleSubmit = async (e) => {
    e.preventDefault()
    const payload = { ...form, dateSolved: form.dateSolved || null }
    try {
      if (editing) await api.put(`/dsa/${editing.id}`, payload)
      else await api.post('/dsa', payload)
      setShowModal(false)
      load()
    } catch {
      setError('Could not save problem.')
    }
  }

  const handleDelete = async (id) => {
    if (!window.confirm('Delete this problem?')) return
    await api.delete(`/dsa/${id}`)
    load()
  }

  const totals = {
    total: problems.length,
    solved: problems.filter((p) => p.status === 'SOLVED').length,
  }

  return (
    <div>
      <PageHeader
        title="DSA Tracker"
        subtitle={`${totals.solved} of ${totals.total} problems solved`}
        action={<button className="btn-primary" onClick={openCreate}>+ Add Problem</button>}
      />
      <ErrorBanner message={error} />

      {loading ? (
        <div className="loading-screen">Loading…</div>
      ) : problems.length === 0 ? (
        <EmptyState text="No problems logged yet. Add your first solved (or to-do) problem." />
      ) : (
        <div className="table-wrap">
          <table>
            <thead>
              <tr>
                <th>Problem</th><th>Topic</th><th>Difficulty</th><th>Platform</th>
                <th>Status</th><th>Date Solved</th><th></th>
              </tr>
            </thead>
            <tbody>
              {problems.map((p) => (
                <tr key={p.id}>
                  <td>{p.problemName}</td>
                  <td>{p.topic}</td>
                  <td><StatusBadge status={p.difficulty} /></td>
                  <td>{p.platform}</td>
                  <td><StatusBadge status={p.status} /></td>
                  <td>{p.dateSolved || '—'}</td>
                  <td className="row-actions">
                    <button onClick={() => openEdit(p)}>✎</button>
                    <button onClick={() => handleDelete(p.id)}>🗑</button>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      )}

      {showModal && (
        <Modal title={editing ? 'Edit Problem' : 'Add Problem'} onClose={() => setShowModal(false)}>
          <form onSubmit={handleSubmit} className="form-grid">
            <label>Problem name</label>
            <input required value={form.problemName} onChange={(e) => setForm({ ...form, problemName: e.target.value })} placeholder="e.g. Two Sum" />

            <label>Topic</label>
            <input value={form.topic || ''} onChange={(e) => setForm({ ...form, topic: e.target.value })} placeholder="e.g. Array" />

            <label>Difficulty</label>
            <select value={form.difficulty} onChange={(e) => setForm({ ...form, difficulty: e.target.value })}>
              <option value="EASY">Easy</option>
              <option value="MEDIUM">Medium</option>
              <option value="HARD">Hard</option>
            </select>

            <label>Platform</label>
            <input value={form.platform || ''} onChange={(e) => setForm({ ...form, platform: e.target.value })} placeholder="e.g. LeetCode" />

            <label>Status</label>
            <select value={form.status} onChange={(e) => setForm({ ...form, status: e.target.value })}>
              <option value="TODO">To Do</option>
              <option value="IN_PROGRESS">In Progress</option>
              <option value="SOLVED">Solved</option>
            </select>

            <label>Date solved</label>
            <input type="date" value={form.dateSolved || ''} onChange={(e) => setForm({ ...form, dateSolved: e.target.value })} />

            <label>Notes</label>
            <textarea value={form.notes || ''} onChange={(e) => setForm({ ...form, notes: e.target.value })} rows={3} />

            <button type="submit" className="btn-primary full">Save</button>
          </form>
        </Modal>
      )}
    </div>
  )
}
