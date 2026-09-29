import React, { useEffect, useState } from 'react'
import api from '../api/axios.js'
import { PageHeader, ErrorBanner, EmptyState, StatusBadge } from '../components/Common.jsx'
import Modal from '../components/Modal.jsx'

const emptyForm = { name: '', provider: '', issueDate: '', credentialId: '', credentialUrl: '', status: 'ACTIVE' }

export default function Certifications() {
  const [items, setItems] = useState([])
  const [error, setError] = useState('')
  const [loading, setLoading] = useState(true)
  const [showModal, setShowModal] = useState(false)
  const [editing, setEditing] = useState(null)
  const [form, setForm] = useState(emptyForm)

  const load = () => {
    setLoading(true)
    api.get('/certifications')
      .then((res) => setItems(res.data))
      .catch(() => setError('Could not load certifications.'))
      .finally(() => setLoading(false))
  }

  useEffect(load, [])

  const openCreate = () => { setEditing(null); setForm(emptyForm); setShowModal(true) }
  const openEdit = (c) => { setEditing(c); setForm({ ...c, issueDate: c.issueDate || '' }); setShowModal(true) }

  const handleSubmit = async (e) => {
    e.preventDefault()
    const payload = { ...form, issueDate: form.issueDate || null }
    try {
      if (editing) await api.put(`/certifications/${editing.id}`, payload)
      else await api.post('/certifications', payload)
      setShowModal(false)
      load()
    } catch {
      setError('Could not save certification.')
    }
  }

  const handleDelete = async (id) => {
    if (!window.confirm('Delete this certification?')) return
    await api.delete(`/certifications/${id}`)
    load()
  }

  return (
    <div>
      <PageHeader
        title="Certifications"
        subtitle="Credentials that back up your skills."
        action={<button className="btn-primary" onClick={openCreate}>+ Add Certification</button>}
      />
      <ErrorBanner message={error} />

      {loading ? (
        <div className="loading-screen">Loading…</div>
      ) : items.length === 0 ? (
        <EmptyState text="No certifications yet." />
      ) : (
        <div className="card-grid">
          {items.map((c) => (
            <div className="item-card" key={c.id}>
              <div className="item-card-top">
                <div>
                  <div className="item-card-title">{c.name}</div>
                  <div className="item-card-meta">{c.provider}</div>
                </div>
                <div className="item-card-actions">
                  <button onClick={() => openEdit(c)}>✎</button>
                  <button onClick={() => handleDelete(c.id)}>🗑</button>
                </div>
              </div>
              <StatusBadge status={c.status} />
              {c.issueDate && <div className="item-card-meta">Issued: {c.issueDate}</div>}
              {c.credentialUrl && <a href={c.credentialUrl} target="_blank" rel="noreferrer">View credential ↗</a>}
            </div>
          ))}
        </div>
      )}

      {showModal && (
        <Modal title={editing ? 'Edit Certification' : 'Add Certification'} onClose={() => setShowModal(false)}>
          <form onSubmit={handleSubmit} className="form-grid">
            <label>Certification name</label>
            <input required value={form.name} onChange={(e) => setForm({ ...form, name: e.target.value })} placeholder="e.g. AWS Certified Developer" />

            <label>Provider</label>
            <input value={form.provider || ''} onChange={(e) => setForm({ ...form, provider: e.target.value })} placeholder="e.g. AWS" />

            <label>Issue date</label>
            <input type="date" value={form.issueDate || ''} onChange={(e) => setForm({ ...form, issueDate: e.target.value })} />

            <label>Credential ID</label>
            <input value={form.credentialId || ''} onChange={(e) => setForm({ ...form, credentialId: e.target.value })} />

            <label>Credential URL</label>
            <input value={form.credentialUrl || ''} onChange={(e) => setForm({ ...form, credentialUrl: e.target.value })} />

            <label>Status</label>
            <select value={form.status} onChange={(e) => setForm({ ...form, status: e.target.value })}>
              <option value="ACTIVE">Active</option>
              <option value="IN_PROGRESS">In Progress</option>
              <option value="EXPIRED">Expired</option>
            </select>

            <button type="submit" className="btn-primary full">Save</button>
          </form>
        </Modal>
      )}
    </div>
  )
}
