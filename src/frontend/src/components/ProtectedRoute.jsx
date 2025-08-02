import React from 'react';
import { Navigate } from 'react-router-dom';

/**
 * A wrapper component that checks for a JWT in localStorage.
 * If the token exists, it renders the child components.
 * If not, it redirects the user to the /login page.
 */
const ProtectedRoute = ({ children }) => {
  const token = localStorage.getItem('jwt_token');

  if (!token) {
    // User is not authenticated, redirect to login page
    return <Navigate to="/login" replace />;
  }

  // User is authenticated, render the page they requested
  return children;
};

export default ProtectedRoute;