import React, { useEffect, useState } from 'react'
import api from '../api/axios.js'
import { PageHeader, ErrorBanner, EmptyState, StatusBadge } from '../components/Common.jsx'
import Modal from '../components/Modal.jsx'

const emptyForm = {
  company: '', role: '', interviewDate: '', interviewType: 'Online',
  round: '', result: 'PENDING', notes: '',
}

export default function Interviews() {
  const [items, setItems] = useState([])
  const [error, setError] = useState('')
  const [loading, setLoading] = useState(true)
  const [showModal, setShowModal] = useState(false)
  const [editing, setEditing] = useState(null)
  const [form, setForm] = useState(emptyForm)

  const load = () => {
    setLoading(true)
    api.get('/interviews')
      .then((res) => setItems(res.data))
      .catch(() => setError('Could not load interviews.'))
      .finally(() => setLoading(false))
  }

  useEffect(load, [])

  const openCreate = () => { setEditing(null); setForm(emptyForm); setShowModal(true) }
  const openEdit = (i) => { setEditing(i); setForm({ ...i, interviewDate: i.interviewDate || '' }); setShowModal(true) }

  const handleSubmit = async (e) => {
    e.preventDefault()
    const payload = { ...form, interviewDate: form.interviewDate || null }
    try {
      if (editing) await api.put(`/interviews/${editing.id}`, payload)
      else await api.post('/interviews', payload)
      setShowModal(false)
      load()
    } catch {
      setError('Could not save interview.')
    }
  }

  const handleDelete = async (id) => {
    if (!window.confirm('Delete this interview?')) return
    await api.delete(`/interviews/${id}`)
    load()
  }

  return (
    <div>
      <PageHeader
        title="Interviews"
        subtitle="Every round, tracked and ready to review."
        action={<button className="btn-primary" onClick={openCreate}>+ Add Interview</button>}
      />
      <ErrorBanner message={error} />

      {loading ? (
        <div className="loading-screen">Loading…</div>
      ) : items.length === 0 ? (
        <EmptyState text="No interviews logged yet." />
      ) : (
        <div className="table-wrap">
          <table>
            <thead>
              <tr><th>Company</th><th>Role</th><th>Date</th><th>Type</th><th>Round</th><th>Result</th><th></th></tr>
            </thead>
            <tbody>
              {items.map((i) => (
                <tr key={i.id}>
                  <td>{i.company}</td>
                  <td>{i.role}</td>
                  <td>{i.interviewDate || '—'}</td>
                  <td>{i.interviewType}</td>
                  <td>{i.round}</td>
                  <td><StatusBadge status={i.result} /></td>
                  <td className="row-actions">
                    <button onClick={() => openEdit(i)}>✎</button>
                    <button onClick={() => handleDelete(i.id)}>🗑</button>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      )}

      {showModal && (
        <Modal title={editing ? 'Edit Interview' : 'Add Interview'} onClose={() => setShowModal(false)}>
          <form onSubmit={handleSubmit} className="form-grid">
            <label>Company</label>
            <input required value={form.company} onChange={(e) => setForm({ ...form, company: e.target.value })} />

            <label>Role</label>
            <input required value={form.role} onChange={(e) => setForm({ ...form, role: e.target.value })} placeholder="e.g. Java Developer" />

            <label>Interview date</label>
            <input type="date" value={form.interviewDate || ''} onChange={(e) => setForm({ ...form, interviewDate: e.target.value })} />

            <label>Type</label>
            <select value={form.interviewType} onChange={(e) => setForm({ ...form, interviewType: e.target.value })}>
              <option>Online</option>
              <option>Offline</option>
              <option>Telephonic</option>
            </select>

            <label>Round</label>
            <input value={form.round || ''} onChange={(e) => setForm({ ...form, round: e.target.value })} placeholder="e.g. Technical" />

            <label>Result</label>
            <select value={form.result} onChange={(e) => setForm({ ...form, result: e.target.value })}>
              <option value="PENDING">Pending</option>
              <option value="CLEARED">Cleared</option>
              <option value="REJECTED">Rejected</option>
            </select>

            <label>Notes</label>
            <textarea value={form.notes || ''} onChange={(e) => setForm({ ...form, notes: e.target.value })} rows={3} />

            <button type="submit" className="btn-primary full">Save</button>
          </form>
        </Modal>
      )}
    </div>
  )
}
