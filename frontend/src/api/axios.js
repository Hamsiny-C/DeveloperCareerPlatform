import axios from 'axios'

// Every request from the frontend to the backend goes through this one
// axios instance. Its baseURL points at our Spring Boot server.
const api = axios.create({
  baseURL: 'http://devcareer-os-alb-1099242622.ap-south-1.elb.amazonaws.com/api',
})

// An "interceptor" runs before every request is sent. Here we grab the
// JWT token we saved in localStorage after login, and attach it to the
// Authorization header - exactly what our backend's JwtAuthFilter expects.
api.interceptors.request.use((config) => {
  const token = localStorage.getItem('token')
  if (token) {
    config.headers.Authorization = `Bearer ${token}`
  }
  return config
})

// If the backend ever responds with 401 (token missing/expired/invalid),
// automatically log the user out and send them back to the login page.
api.interceptors.response.use(
  (response) => response,
  (error) => {
    if (error.response && error.response.status === 401) {
      localStorage.removeItem('token')
      localStorage.removeItem('user')
      if (window.location.pathname !== '/login') {
        window.location.href = '/login'
      }
    }
    return Promise.reject(error)
  }
)

export default api
