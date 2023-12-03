import React, { useEffect, useState } from 'react';
import { Card, CardContent, CardActions, Typography, IconButton, Modal, Box, Fade } from '@mui/material';
import FavoriteIcon from '@mui/icons-material/Favorite';
import CommentIcon from '@mui/icons-material/Comment';
import ShareIcon from '@mui/icons-material/Share';
import DeleteIcon from '@mui/icons-material/Delete';
import styled from '@emotion/styled'; 
import CommentContainer from '../../containers/communityPost/commentsContainer';
import CommentsSection from '../../containers/communityPost/commentsSection';
import { addLike, deletePost, saveComment } from '../../apis/communitypostAPIs/postAPI';

const StyledCard = styled(Card)(({ theme }) => ({
  marginBottom: theme.spacing(2),
  '&:hover': {
    boxShadow: theme.shadows[5],
  },
}));


const onLikeClick = (e, communityPostid) => {
  e.stopPropagation();
  addLike(communityPostid);
}

const onDeleteClick = (e, communityPostid) => {
  e.stopPropagation();
  deletePost(communityPostid);
}


const Post = ({ communityPostid, username, title, content, likes, comments, retweets, onCommentClick, commentsCount  }) => {

  
  return (
      <Fade in={true} timeout={1100}>
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
              <IconButton aria-label="add to favorites" onClick={(e) => onLikeClick(e, communityPostid)}>
                <FavoriteIcon />
                <Typography component="span" sx={{ marginLeft: 0.5 }}>{likes}</Typography>
              </IconButton>
              <IconButton aria-label="comment" onClick={(e) => { e.stopPropagation(); onCommentClick(communityPostid); }}>
                <CommentIcon />
                <Typography component="span" sx={{ marginLeft: 0.5 }}>{commentsCount}</Typography>
              </IconButton>
              <IconButton aria-label="share" onClick={(e) => e.stopPropagation()}>
                <ShareIcon />
                <Typography component="span" sx={{ marginLeft: 0.5 }}>{retweets}</Typography>
              </IconButton>
            </div>
            <IconButton aria-label="delete" onClick={(e) => onDeleteClick(e, communityPostid)}>
              <DeleteIcon />
            </IconButton>
          </CardActions>
          <CommentContainer comments={comments} />
        </StyledCard>
      </Fade>
  );
};

export default Post;
