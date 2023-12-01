import React from "react";
import { Link } from "react-router-dom";
import Logo from "../../components/icons/Logo";
import TextInput from "../../components/TextInput/TextInput";
import "./Login.css";

function Login() {
  return (
    <div className="container">
      <div className="panel">
        <div className="panelHeader">
          <Logo width={39} fill="white" />
          <span className="panelHeaderText">Login to CMUX</span>
        </div>
        <div className="inputs">
          <TextInput text="Email" />
          <TextInput text="Password" />
        </div>
        <Link to="/home" className="loginBtn">
          <span className="loginText">Login</span>
        </Link>
        <div className="loginLinks">
          <a href="/signup">
            <span className="link">Sign up on CMUX</span>
          </a>
        </div>
      </div>
    </div>
  );
}

export default Login;
