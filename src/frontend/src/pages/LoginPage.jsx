// src/pages/LoginPage.jsx
import React, { useState } from 'react';
import axios from 'axios';
import { useNavigate, Link } from 'react-router-dom'; // Import Link

function LoginPage() {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [error, setError] = useState('');
  const navigate = useNavigate();

  const handleLogin = async (e) => {
    e.preventDefault();
    setError('');
    try {
      const response = await axios.post('http://localhost:8080/api/users/login', { email, password });
      localStorage.setItem('jwt_token', response.data.token);
      navigate('/chat');
    } catch (err) {
      console.error("Login failed:", err);
      setError('Login failed. Please check your email and password.');
    }
  };

  return (
    <div className="page-container"> {/* The centering container */}
      <form onSubmit={handleLogin} className="auth-form">
        <h2>Login to PetNexus AI</h2>
        <input type="email" placeholder="Email" value={email} onChange={(e) => setEmail(e.target.value)} required />
        <input type="password" placeholder="Password" value={password} onChange={(e) => setPassword(e.target.value)} required />
        <button type="submit">Login</button>
        {error && <p className="error-message">{error}</p>}
        <p className="form-link">Don't have an account? <Link to="/register">Register here</Link></p>
      </form>
    </div>
  );
}

export default LoginPage;