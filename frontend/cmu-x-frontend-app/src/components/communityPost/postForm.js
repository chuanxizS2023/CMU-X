import React, { useState } from 'react';
import { Dialog, DialogContent, DialogTitle, TextField, DialogActions, Button } from '@mui/material';

const PostForm = ({ open, onClose, onSubmit }) => {
  const [postData, setPostData] = useState({
    title: '',
    content: ''
  });

  const handleChange = (e) => {
    const { name, value } = e.target;
    setPostData({ ...postData, [name]: value });
  };

  const handleSubmit = () => {
    onSubmit(postData);
    onClose(); // Close the modal after submit
  };

  return (
    <Dialog open={open} onClose={onClose}>
      <DialogTitle>Create a New Post</DialogTitle>
      <DialogContent>
        <TextField
          autoFocus
          margin="dense"
          name="title"
          label="Title"
          type="text"
          fullWidth
          variant="outlined"
          value={postData.title}
          onChange={handleChange}
        />
        <TextField
          margin="dense"
          name="content"
          label="Content"
          type="text"
          fullWidth
          multiline
          rows={4}
          variant="outlined"
          value={postData.content}
          onChange={handleChange}
        />
      </DialogContent>
      <DialogActions>
        <Button onClick={onClose}>Cancel</Button>
        <Button onClick={handleSubmit}>Post</Button>
      </DialogActions>
    </Dialog>
  );
};

export default PostForm;
