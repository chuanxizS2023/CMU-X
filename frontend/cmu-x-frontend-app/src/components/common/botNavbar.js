import React from 'react';
import { BottomNavigation, BottomNavigationAction, Paper } from '@mui/material';
import HomeIcon from '@mui/icons-material/Home';
import SearchIcon from '@mui/icons-material/Search';
import NotificationsIcon from '@mui/icons-material/Notifications';
import ChatIcon from '@mui/icons-material/Chat';
import AccountCircleIcon from '@mui/icons-material/AccountCircle';

const BottomBar = () => {
  const [value, setValue] = React.useState('home');

  const handleChange = (event, newValue) => {
    setValue(newValue);
  };

  return (
    <Paper sx={{ position: 'fixed', bottom: 0, left: 0, right: 0, zIndex: 100 }} elevation={3}>
      <BottomNavigation value={value} onChange={handleChange} showLabels
        sx={{
          backgroundColor: 'primary.main', // You can adjust the color using the theme's color palette
          "& .MuiBottomNavigationAction-root": {
            "@media (max-width:768px)": { // Adjust the breakpoint as needed
              minWidth: "auto",
              padding: "6px 0"
            }
          },
          '& .Mui-selected': {
            color: 'secondary.main', // Adjust the selected icon color
          },
          border: '1px solid #ececec', // Add a border
          // Add more styling as needed here
        }}
      >
        <BottomNavigationAction label="Home" value="home" icon={<HomeIcon />} />
        <BottomNavigationAction label="Search" value="search" icon={<SearchIcon />} />
        <BottomNavigationAction label="Notifications" value="notifications" icon={<NotificationsIcon />} />
        <BottomNavigationAction label="Chat" value="chat" icon={<ChatIcon />} />
      </BottomNavigation>
    </Paper>
  );
};

export default BottomBar;
