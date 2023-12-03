import React, { useState } from 'react';
import { TextField, Button, Paper, Grid, Typography } from '@mui/material';
import { useNavigate } from 'react-router-dom';
import TwitterIcon from '../../assets/cmux_logo_no_bg.png';

function Login() {
  const [credentials, setCredentials] = useState({ username: '', password: '' });
  const navigate = useNavigate();

  const handleChange = (event) => {
    setCredentials({ ...credentials, [event.target.name]: event.target.value });
  };

  const handleSubmit = (event) => {
    event.preventDefault();
    // Twitter login logic goes here
    navigate('/home', { replace: true });
  };

  const paperStyle = {
    padding: 20, 
    height: '60vh', 
    width: 280, 
    margin: "20px auto",
    backgroundColor: '#f5f8fa' // Light background color
  };

  const textFieldStyle = { 
    margin: '8px 0', 
    backgroundColor: 'white' // White text fields
  };

  const btnstyle = {
    margin: '8px 0',
    backgroundColor: '#1DA1F2', // Twitter blue
    color: 'white'
  };

  return (
    <Grid container spacing={2} style={{ minHeight: '100vh', backgroundColor: '#f5f8fa' }} alignItems="center" justify="center">
      <Paper elevation={10} style={paperStyle}>
        <Grid align='center'>
          <img src={TwitterIcon} alt="Twitter Logo" width="120" height="100" />
        </Grid>
        <TextField
          label='Username'
          placeholder='Enter username'
          fullWidth
          required
          name="username"
          value={credentials.username}
          onChange={handleChange}
          style={textFieldStyle}
        />
        <TextField
          label='Password'
          placeholder='Enter password'
          type='password'
          fullWidth
          required
          name="password"
          value={credentials.password}
          onChange={handleChange}
          style={textFieldStyle}
        />
        <Button
          type='submit'
          variant="contained"
          fullWidth
          onClick={handleSubmit}
          style={btnstyle}
        >
          Log in with Andrew ID
        </Button>
      </Paper>
    </Grid>
  );
}

export default Login;
