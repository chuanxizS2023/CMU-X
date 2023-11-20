import React from 'react';
import { Fab } from '@mui/material';
import AddIcon from '@mui/icons-material/Add';

const AddPostFab = ({ onClick }) => {
  return (
    <Fab color="primary" aria-label="add" style={{ position: 'fixed', bottom: 16, right: 16 }} onClick={onClick}>
      <AddIcon />
    </Fab>
  );
};

export default AddPostFab;