import React from 'react';
import Post from '../../components/communityPost/post';
import List from '@mui/material/List';

const PostList = ({  }) => {
  const posts = [
    {
      communityPostid: 1,
      username: 'user1',
      title: 'title1',
      content: 'content1',
      likes: 1,
      comments: 1,
      retweets: 1,
    },
    {
      communityPostid: 2,
      username: 'user2',
      title: 'title2',
      content: 'content2',
      likes: 2,
      comments: 2,
      retweets: 2,
    },
    {
      communityPostid: 3,
      username: 'user3',
      title: 'title3',
      content: 'content3',
      likes: 3,
      comments: 3,
      retweets: 3,
    },
  ];

  return (
    <List sx={{ width: '100%', bgcolor: 'background.paper' }}>
      {posts.map((post) => (
        <Post key={post.communityPostid} {...post} />
      ))}
    </List>
  );
};

export default PostList;
