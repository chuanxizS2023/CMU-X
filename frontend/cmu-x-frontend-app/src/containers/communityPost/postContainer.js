import React from 'react';
import Post from '../../components/communityPost/post';
import List from '@mui/material/List';

const PostList = ({ posts, onCommentClick  }) => {


  return (
    <List sx={{ width: '100%', bgcolor: 'background.paper' }}>
      {posts.map((post) => (
        <Post key={post.communityPostid} {...post} onCommentClick={onCommentClick} />
      ))}
    </List>
  );
};

export default PostList;
