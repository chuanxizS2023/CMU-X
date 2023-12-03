import React from 'react';
import { Typography, Box } from '@mui/material';
import styled from '@emotion/styled';

const CommentBox = styled(Box)(({ theme }) => ({
  backgroundColor: theme.palette.grey[100],
  padding: theme.spacing(1),
  margin: theme.spacing(1, 0),
  borderRadius: theme.shape.borderRadius,
}));

const CommentsSection = ({ comments }) => {
  return (
    <Box>
      {comments.map((comment, index) => (
        <CommentBox key={index}>
          <Typography variant="body1">{comment.content}</Typography>
          <Typography variant="body2" color="text.secondary">Author ID: {comment.author_id}</Typography>
        </CommentBox>
      ))}
    </Box>
  );
};

export default CommentsSection;
