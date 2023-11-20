import React from 'react';
import { Fab } from '@mui/material';
import { useNavigate } from 'react-router-dom';
import AddIcon from '@mui/icons-material/Add';

const AddPostFab = ({ onClick }) => {
    const navigate = useNavigate();

  return (
    <Fab color="primary" aria-label="add" style={{ position: 'fixed', bottom: 55, right: 16, opacity:"0.75" }} onClick={navigate('new-post')}>
      <AddIcon/>
    </Fab>
  );
};

export default AddPostFab;
