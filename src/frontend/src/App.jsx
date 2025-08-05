import './App.css';
import { Routes, Route } from 'react-router-dom';
import LoginPage from './pages/LoginPage';
import ChatPage from './pages/ChatPage';
import RegisterPage from './pages/RegisterPage';
import PetsPage from './pages/PetsPage';
import ProtectedRoute from './components/ProtectedRoute';
import MainLayout from './components/MainLayout'; // <-- Import the layout

function App() {
  return (
    <Routes>
      {/* Public routes */}
      <Route path="/login" element={<LoginPage />} />
      <Route path="/register" element={<RegisterPage />} />

      {/* Protected routes will now render inside the MainLayout */}
      <Route
        path="/"
        element={
          <ProtectedRoute>
            <MainLayout />
          </ProtectedRoute>
        }
      >
        <Route path="pets" element={<PetsPage />} />
        <Route path="chat" element={<ChatPage />} />
        {/* The default protected route will redirect to /pets */}
        <Route index element={<PetsPage />} />
      </Route>
    </Routes>
  );
}

export default App;