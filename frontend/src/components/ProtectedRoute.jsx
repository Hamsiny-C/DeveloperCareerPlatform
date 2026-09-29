import React from 'react'
import { Navigate } from 'react-router-dom'
import { useAuth } from '../api/AuthContext.jsx'
import AppLayout from './AppLayout.jsx'

// Wraps any page that requires the user to be logged in. If there's no
// user in our AuthContext, we redirect straight to /login instead of
// rendering the page.
export default function ProtectedRoute({ children }) {
  const { user } = useAuth()

  if (!user) {
    return <Navigate to="/login" replace />
  }

  return <AppLayout>{children}</AppLayout>
}
