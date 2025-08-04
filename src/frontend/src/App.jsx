// src/App.jsx
import './App.css';
import { Routes, Route } from 'react-router-dom';
import LoginPage from './pages/LoginPage';
import ChatPage from './pages/ChatPage';
import RegisterPage from './pages/RegisterPage';
import ProtectedRoute from './components/ProtectedRoute'; // <-- 1. Import the guardian

function App() {
  return (
    <Routes>
      {/* Public routes */}
      <Route path="/" element={<LoginPage />} />
      <Route path="/login" element={<LoginPage />} />
      <Route path="/register" element={<RegisterPage />} />

      {/* Protected route */}
      <Route
        path="/chat"
        element={
          <ProtectedRoute> {/* <-- 2. Wrap the page with the guardian */}
            <ChatPage />
          </ProtectedRoute>
        }
      />
    </Routes>
  );
}

export default App;