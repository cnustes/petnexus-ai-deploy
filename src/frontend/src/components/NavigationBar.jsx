import React from 'react';
import { Link, useNavigate } from 'react-router-dom';

function NavigationBar() {
  const navigate = useNavigate();

  const handleLogout = () => {
    // Clear the token from storage
    localStorage.removeItem('jwt_token');
    // Redirect to the login page
    navigate('/login');
  };

  return (
    <nav className="main-nav">
      <div className="nav-left">
        <Link to="/pets" className="nav-logo">
          <img src="/logo.png" alt="PetNexus AI Logo" />
          <span>PetNexus AI</span>
        </Link>
      </div>
      <div className="nav-right">
        <Link to="/pets" className="nav-link">My Pets</Link>
        <Link to="/chat" className="nav-link">Chat</Link>
        <button onClick={handleLogout} className="logout-button">Logout</button>
      </div>
    </nav>
  );
}

export default NavigationBar;