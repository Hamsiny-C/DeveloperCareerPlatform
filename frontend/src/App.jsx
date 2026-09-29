import React from 'react'
import { Routes, Route } from 'react-router-dom'
import { AuthProvider } from './api/AuthContext.jsx'
import ProtectedRoute from './components/ProtectedRoute.jsx'

import Login from './pages/Login.jsx'
import Register from './pages/Register.jsx'
import Dashboard from './pages/Dashboard.jsx'
import Skills from './pages/Skills.jsx'
import Dsa from './pages/Dsa.jsx'
import Learning from './pages/Learning.jsx'
import Projects from './pages/Projects.jsx'
import Certifications from './pages/Certifications.jsx'
import JobApplications from './pages/JobApplications.jsx'
import Interviews from './pages/Interviews.jsx'
import Profile from './pages/Profile.jsx'

export default function App() {
  return (
    <AuthProvider>
      <Routes>
        <Route path="/login" element={<Login />} />
        <Route path="/register" element={<Register />} />

        <Route path="/" element={<ProtectedRoute><Dashboard /></ProtectedRoute>} />
        <Route path="/skills" element={<ProtectedRoute><Skills /></ProtectedRoute>} />
        <Route path="/dsa" element={<ProtectedRoute><Dsa /></ProtectedRoute>} />
        <Route path="/learning" element={<ProtectedRoute><Learning /></ProtectedRoute>} />
        <Route path="/projects" element={<ProtectedRoute><Projects /></ProtectedRoute>} />
        <Route path="/certifications" element={<ProtectedRoute><Certifications /></ProtectedRoute>} />
        <Route path="/applications" element={<ProtectedRoute><JobApplications /></ProtectedRoute>} />
        <Route path="/interviews" element={<ProtectedRoute><Interviews /></ProtectedRoute>} />
        <Route path="/profile" element={<ProtectedRoute><Profile /></ProtectedRoute>} />
      </Routes>
    </AuthProvider>
  )
}
