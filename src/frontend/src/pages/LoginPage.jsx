import React, { useState } from 'react';
import axios from 'axios';
import { useNavigate, Link } from 'react-router-dom';
import { ClipLoader } from 'react-spinners'; // <-- 1. Import the spinner

function LoginPage() {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [error, setError] = useState('');
  const [isLoading, setIsLoading] = useState(false); // <-- 2. Create loading state
  const navigate = useNavigate();

  const handleLogin = async (e) => {
    e.preventDefault();
    setError('');
    setIsLoading(true); // <-- 3. Set loading to true when the process starts

    try {
      const response = await axios.post(`${import.meta.env.VITE_API_URL}/api/users/login`, {
        email,
        password
      });

      localStorage.setItem('jwt_token', response.data.token);
      navigate('/chat');

    } catch (err) {
      console.error("Login failed:", err);
      setError('Login failed. Please check your email and password.');
    } finally {
      setIsLoading(false); // <-- 4. Set loading to false when it's all done (success or fail)
    }
  };

  return (
    <div className="page-container">
      <form onSubmit={handleLogin} className="auth-form">
        <h2>Login to PetNexus AI</h2>
        <input
          type="email"
          placeholder="Email"
          value={email}
          onChange={(e) => setEmail(e.target.value)}
          required
        />
        <input
          type="password"
          placeholder="Password"
          value={password}
          onChange={(e) => setPassword(e.target.value)}
          required
        />
        {/* --- 5. Update the button --- */}
        <button type="submit" disabled={isLoading}>
          {isLoading ? <ClipLoader color="#fff" size={20} /> : 'Login'}
        </button>
        {error && <p className="error-message">{error}</p>}
        <p className="form-link">Don't have an account? <Link to="/register">Register here</Link></p>
      </form>
    </div>
  );
}

export default LoginPage;