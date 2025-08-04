import React, { useState } from 'react';
import axios from 'axios';
import { useNavigate, Link } from 'react-router-dom';

function RegisterPage() {
  const [name, setName] = useState('');
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [error, setError] = useState('');
  const navigate = useNavigate();

  const handleRegister = async (e) => {
    e.preventDefault();
    setError('');
    try {
      // --- CHANGES ARE HERE ---

      // 1. Capture the response from the API call
      const response = await axios.post(`${import.meta.env.VITE_API_URL}/api/users/register`, { name, email, password });

      // 2. Get the token from the response
      const token = response.data.token;

      // 3. Store the token in local storage, just like in login
      localStorage.setItem('jwt_token', token);

      // 4. Redirect directly to the chat page
      navigate('/chat');

      // ------------------------

    } catch (err) {
      console.error("Registration failed:", err);
      setError('Registration failed. The email might already be in use.');
    }
  };

  return (
    <div className="page-container">
      <form onSubmit={handleRegister} className="auth-form">
        <h2>Register for PetNexus AI</h2>
        <input type="text" placeholder="Name" value={name} onChange={(e) => setName(e.target.value)} required />
        <input type="email" placeholder="Email" value={email} onChange={(e) => setEmail(e.target.value)} required />
        <input type="password" placeholder="Password" value={password} onChange={(e) => setPassword(e.target.value)} required />
        <button type="submit">Register</button>
        {error && <p className="error-message">{error}</p>}
        <p className="form-link">Already have an account? <Link to="/login">Login here</Link></p>
      </form>
    </div>
  );
}

export default RegisterPage;