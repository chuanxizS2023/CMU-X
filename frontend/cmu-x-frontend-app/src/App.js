import React from 'react';
import { BrowserRouter as Router, Route, Routes } from "react-router-dom";
import { ThemeProvider, createTheme } from '@mui/material/styles';
import Login from './pages/auth/login';
import CommunityPage from './pages/communityPost/communityPostPage';
import LandingPage from './pages/LandingPage';
import HomePage from './pages/HomePage';

const theme = createTheme();

function App() {
  return (
    <ThemeProvider theme={theme}>
      <Router>
        <Routes>
          <Route path="/" element={<LandingPage />} />
          <Route path="login" element={<Login />}/>
          <Route path="communityPost" element={<CommunityPage />}/>
        </Routes>
      </Router>
    </ThemeProvider>
  );
}

export default App;
