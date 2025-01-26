import React, { createContext, useContext, useState } from 'react';
import axios from 'axios';


export const AuthContext = createContext();
export const useAuth = () => useContext(AuthContext);

export const AuthProvider = ({ children }) => {
  const [currentUser, setCurrentUser] = useState(() =>{

    // Check localStorage for persisted user
    const storedUser = localStorage.getItem("currentUser");
    return storedUser ? JSON.parse(storedUser) : null;
  });
  

  // Register function
  const register = async (username, password, email) => {
    try {
      const response = await axios.post("http://localhost:8080/Employee/register", {
        username,
        password,
        email,
      });
      return { success: true, message: response.data };
    } catch (error) {
      return { success: false, message: error.response?.data || "An error occurred!" };
    }
  };

  // Login function
  const login = async (username, password) => {
    try {
      const response = await axios.post("http://localhost:8080/Employee/login", null, {
        params: { username, password },
      });
      const user = response.data;

    // Save user to localStorage for persistence
    localStorage.setItem("currentUser", JSON.stringify(user));
      setCurrentUser(response.data);
      return { success: true, message: "Login successful!" };
    } catch (error) {
      return { success: false, message: "Invalid credentials!" };
    }
  };

  const logout = () => {
    setCurrentUser(null);
    localStorage.removeItem("currentUser"); // Clear user from localStorage
    
  };

  return (
    <AuthContext.Provider value={{ currentUser, register, login, logout }}>
      {children}
    </AuthContext.Provider>
  );
};