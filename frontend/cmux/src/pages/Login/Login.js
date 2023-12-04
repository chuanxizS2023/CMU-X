import React, { useContext,useState } from "react";
import {AuthContext} from '../../components/AuthProvider';
import { Link, useHistory } from "react-router-dom";
import Logo from "../../components/icons/Logo";
import TextInput from "../../components/TextInput/TextInput";
import "./Login.css";

function Login() {
  // State hooks to store the user's email and password
  const [userName, setUserName] = useState('');
  const [password, setPassword] = useState('');
  const [errorMessage, setErrorMessage] = useState('');

  const { signIn } = useContext(AuthContext);

  // useHistory hook to programmatically navigate
  const history = useHistory();

  // Function to update state with the user's email
  const handleUserNameChange = (e) => {
    console.log('Username change:', e.target.value);
    setUserName(e.target.value);
  };

  // Function to update state with the user's password
  const handlePasswordChange = (e) => {
    setPassword(e.target.value);
  };

  // Function to handle the login when the user submits the form
  const handleLogin = async () => {
    console.log(userName, password);
    try {
      // Replace `YOUR_BACKEND_ENDPOINT` with your actual login API endpoint
      const response = await fetch('http://localhost:5001/auth/signin', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({
          username: userName,
          password: password
        }),
      });

      const json = await response.json();
    
      if (response.ok) {
        // Handle successful login here
        // You might want to store the tokens in local storage or context
        // and redirect the user to the home page
        console.log(json.userName);
        signIn({
          accessToken: json.accessToken,
          refreshToken: json.refreshToken,
          userId: json.userId,
          username: json.username,
        });
        history.push('/home');
      } else {
        // If the login wasn't successful, display the error message
        setErrorMessage(json.message || 'Login failed');
      }
    } catch (error) {
      // Handle network error here
      console.error('Login error:', error);
      setErrorMessage('An error occurred. Please try again later.');
    }
  };

  return (
    <div className="container">
      <div className="panel">
        <div className="panelHeader">
          <Logo width={39} fill="white" />
          <span className="panelHeaderText">Login to CMUX</span>
        </div>
        <div className="inputs">
          <TextInput type="text" value={userName} onChange={handleUserNameChange} text = "Username" />
          <TextInput type="password" value={password} onChange={handlePasswordChange} text="Password" />
        </div>
        <button onClick={handleLogin} className="loginBtn">
          <span className="loginText">Login</span>
        </button>
        {errorMessage && <div className="error-message">{errorMessage}</div>}
        <div className="loginLinks">
          <Link to="/signup">
            <span className="link">Sign up on CMUX</span>
          </Link>
        </div>
      </div>
    </div>
  );
}

export default Login;
