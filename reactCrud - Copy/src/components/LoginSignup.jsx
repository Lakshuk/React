import React, { useState, useContext } from 'react';
import { useNavigate } from 'react-router-dom';
import { AuthContext } from '../context/AuthContext';
import './LoginSignup.css';

const LoginSignup = () => {
  const { register, login } = useContext(AuthContext);
  const [isLogin, setIsLogin] = useState(true);
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [message, setMessage] = useState('');
  const navigate = useNavigate();

  const handleSubmit = (e) => {
    e.preventDefault();
    if (isLogin) {
      const result = login(username, password);
      setMessage(result.message);
      if (result.success) {
        navigate('/employee-list'); // Redirect to add-employee page after login
      }
    } else {
      const result = register(username, password);
      setMessage(result.message);
      if (result.success) {
        setIsLogin(true); // Switch to login after successful registration
      }
    }
  };

  return (
    <div className="container">
      <h2>{isLogin ? 'LOGIN' : 'SIGN UP'}</h2>
      <form onSubmit={handleSubmit}>
        <label>Username:</label>
        <input
          type="text"
          value={username}
          onChange={(e) => setUsername(e.target.value)}
          required
        />

  
        <label>Password:</label>
        <input
          type="password"
          value={password}
          onChange={(e) => setPassword(e.target.value)}
          required
        />
        <br />
 
        <button type="submit">{isLogin ? 'Login' : 'Sign Up'}</button>
        <br />
        
      </form>
      {message && <p>{message}</p>}
      <div className="toggle-button">
      <button onClick={() => setIsLogin(!isLogin)}>
        {isLogin ? "Don't have an account? Sign Up" : 'Already have an account? Login'}
      </button>
    </div>
    </div>
  );
};

export default LoginSignup;