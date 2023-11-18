import React, { useState } from 'react';
import { TextField, Button, Paper, Grid, Typography } from '@mui/material';

function Login() {
  const [credentials, setCredentials] = useState({ email: '', password: '' });

  const handleChange = (event) => {
    setCredentials({ ...credentials, [event.target.name]: event.target.value });
  };

  const handleSubmit = (event) => {
    event.preventDefault();
    // Handle login logic here
    console.log('Logging in with', credentials);
  };

  const paperStyle = {
    padding: 20, 
    height: '70vh', 
    width: 280, 
    margin: "20px auto"
  };

  const btnstyle = { margin: '8px 0' };

  return (
    <Grid container spacing={2} style={{ minHeight: '100vh' }} alignItems="center" justify="center">
      <Paper elevation={10} style={paperStyle}>
        <Grid align='center'>
          <Typography variant='h5'>Sign in</Typography>
        </Grid>
        <TextField
          label='Email'
          placeholder='Enter email'
          fullWidth
          required
          name="email"
          value={credentials.email}
          onChange={handleChange}
          style={{ margin: '8px 0' }}
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
          style={{ margin: '8px 0' }}
        />
        <Button
          type='submit'
          color='primary'
          variant="contained"
          fullWidth
          onClick={handleSubmit}
          style={btnstyle}
        >
          Sign in
        </Button>
      </Paper>
    </Grid>
  );
}

export default Login;
