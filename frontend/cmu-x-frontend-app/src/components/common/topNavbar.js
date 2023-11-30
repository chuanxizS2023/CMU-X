import React from 'react';
import { AppBar, Toolbar, IconButton, Typography, Box } from '@mui/material';
import MenuIcon from '@mui/icons-material/Menu';
import cmuLogo from '../../assets/cmuX_logo.jpeg'; // Make sure the path is correct

const TopBar = () => {
  return (
    <AppBar position="static">
      <Toolbar>
        <IconButton edge="start" color="inherit" aria-label="menu" sx={{ mr: 2 }}>
          <MenuIcon />
        </IconButton>
        <Box sx={{ flexGrow: 1 }}>
          <img src={cmuLogo} alt="CMU-X Logo" style={{ height: '50px' }} /> {/* Logo displayed as an image */}
        </Box>
      </Toolbar>
    </AppBar>
  );
};

export default TopBar;
