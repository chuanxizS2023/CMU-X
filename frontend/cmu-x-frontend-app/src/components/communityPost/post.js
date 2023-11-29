import React from 'react';
import { Card, CardContent, CardActions, Typography, IconButton } from '@mui/material';
import FavoriteIcon from '@mui/icons-material/Favorite';
import CommentIcon from '@mui/icons-material/Comment';
import ShareIcon from '@mui/icons-material/Share';
import DeleteIcon from '@mui/icons-material/Delete';
import styled from '@emotion/styled'; 
import { addLike, deletePost, saveComment } from '../../apis/communitypostAPIs/postAPI';

const StyledCard = styled(Card)(({ theme }) => ({
  marginBottom: theme.spacing(2),
  '&:hover': {
    boxShadow: theme.shadows[5],
  },
}));

const onLikeClick = (communityPostid) => {
  console.log("like clicked with id: ", communityPostid);
  const response = addLike(communityPostid);
}

const onDeleteClick = (communityPostid) => {
  console.log("delete clicked with id: ", communityPostid);
  const response = deletePost(communityPostid);
}

const Post = ({ communityPostid, username, title, content, likes, comments, retweets, onCommentClick  }) => {
  return (
    <StyledCard>
      <CardContent>
        <Typography gutterBottom variant="h5" component="div">
          {title}
        </Typography>
        <Typography variant="body2" color="text.secondary">
          {content}
        </Typography>
      </CardContent>
      <CardActions disableSpacing sx={{ justifyContent: 'space-between' }}>
        <div>
          <IconButton aria-label="add to favorites" onClick={() => onLikeClick(communityPostid)}>
            <FavoriteIcon />
            <Typography component="span" sx={{ marginLeft: 0.5 }}>{likes}</Typography>
          </IconButton>
          <IconButton aria-label="comment" onClick={() => onCommentClick(communityPostid)}>
            <CommentIcon />
          </IconButton>
          <IconButton aria-label="share">
            <ShareIcon />
            <Typography component="span" sx={{ marginLeft: 0.5 }}>{retweets}</Typography>
          </IconButton>
        </div>
        <IconButton aria-label="delete" onClick={() => onDeleteClick(communityPostid)}>
          <DeleteIcon />
        </IconButton>
      </CardActions>
    </StyledCard>
  );
};

export default Post;
