import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import './LoginSignup.css';
import { useAuth } from '../context/AuthContext';

const LoginSignup = () => {
  const [isLogin, setIsLogin] = useState(true);
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [email, setEmail] = useState('');
  const [message, setMessage] = useState('');
  const navigate = useNavigate();
  const { register, login } = useAuth();

  const registerUser = async () => {
    try {
      const response = await register(username, password, email);
      setMessage(response.message);
      if (response.success) {
        console.log('Registration successful, navigating to /');
        navigate('/'); // Navigate to a different route
      }
    } catch (error) {
      setMessage(error.message);
    }
  };


  const loginUser = async () => {
    try {
      const response = await login(username, password);
      setMessage(response.message);
      if (response.success) {
        // const userData = response.data;
        // if (typeof userData === 'object') {
        //   localStorage.setItem('user', JSON.stringify(userData));
        console.log("Login Successfull");
        navigate('/');
      }
    } catch (error) {
      setMessage(error.message);
    }
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    if (isLogin) {
      loginUser();
    } else {
      registerUser();
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
        {!isLogin && (
          <>
            <label>Email:</label>
            <input
              type="email"
              value={email}
              onChange={(e) => setEmail(e.target.value)}
              required
            />
          </>
        )}
        <label>Password:</label>
        <input
          type="password"
          value={password}
          onChange={(e) => setPassword(e.target.value)}
          required
        />
        <button type="submit">{isLogin ? 'Login' : 'Sign Up'}</button>
      </form>
      {message && <p>{message}</p>}
      <button onClick={() => setIsLogin(!isLogin)}>
        {isLogin ? "Don't have an account? Sign Up" : 'Already have an account? Login'}
      </button>
    </div>
  );
};

export default LoginSignup;
