import React, { useEffect, useState } from 'react'
import api from '../api/axios.js'
import { PageHeader, ErrorBanner, EmptyState, StatusBadge } from '../components/Common.jsx'
import Modal from '../components/Modal.jsx'

const emptyForm = {
  company: '', role: '', location: '', applicationDate: '',
  status: 'APPLIED', jobUrl: '', notes: '',
}

const STATUSES = ['APPLIED', 'SCREENING', 'INTERVIEW', 'OFFER', 'REJECTED', 'WITHDRAWN']

export default function JobApplications() {
  const [items, setItems] = useState([])
  const [error, setError] = useState('')
  const [loading, setLoading] = useState(true)
  const [showModal, setShowModal] = useState(false)
  const [editing, setEditing] = useState(null)
  const [form, setForm] = useState(emptyForm)
  const [filter, setFilter] = useState('ALL')

  const load = () => {
    setLoading(true)
    api.get('/applications')
      .then((res) => setItems(res.data))
      .catch(() => setError('Could not load applications.'))
      .finally(() => setLoading(false))
  }

  useEffect(load, [])

  const openCreate = () => { setEditing(null); setForm(emptyForm); setShowModal(true) }
  const openEdit = (a) => { setEditing(a); setForm({ ...a, applicationDate: a.applicationDate || '' }); setShowModal(true) }

  const handleSubmit = async (e) => {
    e.preventDefault()
    const payload = { ...form, applicationDate: form.applicationDate || null }
    try {
      if (editing) await api.put(`/applications/${editing.id}`, payload)
      else await api.post('/applications', payload)
      setShowModal(false)
      load()
    } catch {
      setError('Could not save application.')
    }
  }

  const handleDelete = async (id) => {
    if (!window.confirm('Delete this application?')) return
    await api.delete(`/applications/${id}`)
    load()
  }

  const visible = filter === 'ALL' ? items : items.filter((i) => i.status === filter)

  return (
    <div>
      <PageHeader
        title="Job Applications"
        subtitle={`${items.length} total applications`}
        action={<button className="btn-primary" onClick={openCreate}>+ Add Application</button>}
      />
      <ErrorBanner message={error} />

      <div className="filter-row">
        <button className={`chip-filter ${filter === 'ALL' ? 'active' : ''}`} onClick={() => setFilter('ALL')}>All</button>
        {STATUSES.map((s) => (
          <button key={s} className={`chip-filter ${filter === s ? 'active' : ''}`} onClick={() => setFilter(s)}>
            {s.charAt(0) + s.slice(1).toLowerCase()}
          </button>
        ))}
      </div>

      {loading ? (
        <div className="loading-screen">Loading…</div>
      ) : visible.length === 0 ? (
        <EmptyState text="No applications match this filter." />
      ) : (
        <div className="table-wrap">
          <table>
            <thead>
              <tr><th>Company</th><th>Role</th><th>Location</th><th>Applied</th><th>Status</th><th></th></tr>
            </thead>
            <tbody>
              {visible.map((a) => (
                <tr key={a.id}>
                  <td>{a.company}</td>
                  <td>{a.role}</td>
                  <td>{a.location || '—'}</td>
                  <td>{a.applicationDate || '—'}</td>
                  <td><StatusBadge status={a.status} /></td>
                  <td className="row-actions">
                    <button onClick={() => openEdit(a)}>✎</button>
                    <button onClick={() => handleDelete(a.id)}>🗑</button>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      )}

      {showModal && (
        <Modal title={editing ? 'Edit Application' : 'Add Application'} onClose={() => setShowModal(false)}>
          <form onSubmit={handleSubmit} className="form-grid">
            <label>Company</label>
            <input required value={form.company} onChange={(e) => setForm({ ...form, company: e.target.value })} />

            <label>Role</label>
            <input required value={form.role} onChange={(e) => setForm({ ...form, role: e.target.value })} placeholder="e.g. Java Developer" />

            <label>Location</label>
            <input value={form.location || ''} onChange={(e) => setForm({ ...form, location: e.target.value })} />

            <label>Application date</label>
            <input type="date" value={form.applicationDate || ''} onChange={(e) => setForm({ ...form, applicationDate: e.target.value })} />

            <label>Status</label>
            <select value={form.status} onChange={(e) => setForm({ ...form, status: e.target.value })}>
              {STATUSES.map((s) => <option key={s} value={s}>{s.charAt(0) + s.slice(1).toLowerCase()}</option>)}
            </select>

            <label>Job URL</label>
            <input value={form.jobUrl || ''} onChange={(e) => setForm({ ...form, jobUrl: e.target.value })} />

            <label>Notes</label>
            <textarea value={form.notes || ''} onChange={(e) => setForm({ ...form, notes: e.target.value })} rows={3} />

            <button type="submit" className="btn-primary full">Save</button>
          </form>
        </Modal>
      )}
    </div>
  )
}
