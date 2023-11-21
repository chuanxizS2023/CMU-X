import React from 'react';
import { Card, CardContent, CardActions, Typography, IconButton } from '@mui/material';
import FavoriteIcon from '@mui/icons-material/Favorite';
import CommentIcon from '@mui/icons-material/Comment';
import ShareIcon from '@mui/icons-material/Share';
import styled from '@emotion/styled'; 
import { addLike } from '../../apis/communitypostAPIs/postAPI';

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

const Post = ({ communityPostid, username, title, content, likes, comments, retweets }) => {
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
      <CardActions disableSpacing>
        <IconButton aria-label="add to favorites" onClick={() => onLikeClick(communityPostid)}>
          <FavoriteIcon />
          <Typography component="span" sx={{ marginLeft: 0.5 }}>{likes}</Typography>
        </IconButton>
        <IconButton aria-label="comment">
          <CommentIcon />
          <Typography component="span" sx={{ marginLeft: 0.5 }}>{comments}</Typography>
        </IconButton>
        <IconButton aria-label="share">
          <ShareIcon />
          <Typography component="span" sx={{ marginLeft: 0.5 }}>{retweets}</Typography>
        </IconButton>
      </CardActions>
    </StyledCard>
  );
};

export default Post;
