import React, { createContext, useContext, useState } from 'react'
import api from './axios.js'

// React Context lets us share the "current logged-in user" with every
// page/component in the app, without passing it down as props manually
// through every single level ("prop drilling").
const AuthContext = createContext(null)

export function AuthProvider({ children }) {
  const [user, setUser] = useState(() => {
    const saved = localStorage.getItem('user')
    return saved ? JSON.parse(saved) : null
  })

  const login = async (email, password) => {
    const res = await api.post('/auth/login', { email, password })
    saveSession(res.data)
    return res.data
  }

  const register = async (fullName, email, password) => {
    const res = await api.post('/auth/register', { fullName, email, password })
    saveSession(res.data)
    return res.data
  }

  const saveSession = (data) => {
    localStorage.setItem('token', data.token)
    const userData = { id: data.userId, fullName: data.fullName, email: data.email, role: data.role }
    localStorage.setItem('user', JSON.stringify(userData))
    setUser(userData)
  }

  const logout = () => {
    localStorage.removeItem('token')
    localStorage.removeItem('user')
    setUser(null)
  }

  return (
    <AuthContext.Provider value={{ user, setUser, login, register, logout }}>
      {children}
    </AuthContext.Provider>
  )
}

// A custom hook - any component can call useAuth() to get { user, login,
// register, logout } instead of importing useContext + AuthContext every time.
export function useAuth() {
  return useContext(AuthContext)
}
