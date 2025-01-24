import React, { createContext, useContext, useState } from 'react';
//import axios form 'axios';

// Create AuthContext
export const AuthContext = createContext();
export const useAuth = () => useContext(AuthContext);

// AuthProvider Component
export const AuthProvider = ({ children }) => {

  const [isAuthenticated, setIsAuthenticated] = useState(false);
  const loginUser = () => setIsAuthenticated(true);
  const logoutt = () => setIsAuthenticated(false);
  const [currentUser, setCurrentUser] = useState(
    JSON.parse(localStorage.getItem('user')) || null
  );

  // Register function
  const register = (username, password, email) => {
    const users = JSON.parse(localStorage.getItem('users')) || [];
    const existingUser = users.find((user) => user.username === username);

    if (existingUser) {
      return { success: false, message: 'User already exists!' };
    }

    users.push({ username, password, email });
    localStorage.setItem('users', JSON.stringify(users));
    return { success: true, message: 'Registration successful!' };
  };

  // Login function
  const login = (username, password) => {
    const users = JSON.parse(localStorage.getItem('users')) || [];
    const user = users.find(
      (user) => user.username === username && user.password === password
    );

    if (user) {
      setCurrentUser(user);
      localStorage.setItem('user', JSON.stringify(user));
      return { success: true, message: 'Login successful!' };
    } else {
      return { success: false, message: 'Invalid credentials!' };
    }
  };

  // Logout function
  const logout = () => {
    localStorage.removeItem('user');
    setCurrentUser(null);
  };

  return (
    <AuthContext.Provider value={{ currentUser, register, login, logout, loginUser, logoutt }}>
      {children}
    </AuthContext.Provider>
  );
};

