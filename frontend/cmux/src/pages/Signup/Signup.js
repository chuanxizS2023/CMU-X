import React, { useState, useContext } from "react";
import { AuthContext } from '../../components/AuthProvider';
import { useHistory } from "react-router-dom";
import Logo from "../../components/icons/Logo";
import TextInput from "../../components/TextInput/TextInput";
import "./Signup.css";

function Signup() {
  const [username, setUsername] = useState('');
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [errorMessage, setErrorMessage] = useState('');

  const { signIn } = useContext(AuthContext);
  const history = useHistory();

  const handleSignup = async () => {
    try {
      const response = await fetch(`${process.env.REACT_APP_URL}auth/signup`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({
          username,
          email,
          password
        }),
      });

      const data = await response.json();

      if (response.ok) {
        // 自动登录
        await handleLogin(username, password);
      } else {
        setErrorMessage(data.message || 'Signup failed');
      }
    } catch (error) {
      console.error('Signup error:', error);
      setErrorMessage('An error occurred. Please try again later.');
    }
  };

  const handleLogin = async (username, password) => {
    try {
      const response = await fetch(`${process.env.REACT_APP_URL}auth/signin`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({
          username,
          password
        }),
      });

      const json = await response.json();

      if (response.ok) {
        signIn({
          accessToken: json.accessToken,
          refreshToken: json.refreshToken,
          userId: json.userId,
          username: json.username,
        });
        history.push('/home');
      } else {
        setErrorMessage(json.message || 'Login failed');
      }
    } catch (error) {
      console.error('Login error:', error);
      setErrorMessage('An error occurred. Please try again later.');
    }
  };
  return (
    <div className="signUpContainer">
      <div className="card">
        <div className="signuplogo">
          <Logo width={26} height={53} fill="white" />
        </div>
        <div className="signupHeader">
          <span>Create your account</span>
        </div>
        <TextInput text="Username" onChange={e => setUsername(e.target.value)} />
        <TextInput text="Email" onChange={e => setEmail(e.target.value)} />
        <TextInput text="Password" type="password" onChange={e => setPassword(e.target.value)} />
        <div className="acceptTerm">
          <span>
            When you register, you agree to the
            <span className="acceptTermBlue"> terms of service</span> and the
            <span className="acceptTermBlue"> Privacy Policy</span>, including
            the use of <span className="acceptTermBlue">cookies</span>. When you
            set your
            <span className="acceptTermBlue"> privacy options </span>
            accordingly, others can find you by email or phone number.
          </span>
        </div>
        <button className="signupBtn" onClick={handleSignup}>
          <span className="signupText">Sign up</span>
        </button>
        {errorMessage && <div className="error-message">{errorMessage}</div>}
      </div>
    </div>
  );
}

export default Signup;
