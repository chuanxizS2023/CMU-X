import React from 'react';
import { BrowserRouter as Router, Route, Routes } from "react-router-dom";
import Login from './pages/auth/login';
import CommunityPage from './pages/communityPost/communityPostPage';

function App() {
  return (
    <Router>
      <Routes>
        <Route path="/" element={<Login />} />
        <Route path="communityPost" element={<CommunityPage />}/>
      </Routes>
    </Router>
  );
}

export default App;
