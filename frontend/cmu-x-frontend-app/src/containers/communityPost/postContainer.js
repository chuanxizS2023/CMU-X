import React, { useState, useEffect } from 'react';
import Post from '../../components/communityPost/post';

const PostList = () => {
    const posts = [
        {
          id: 1,
          username: 'johndoe',
          title: 'Hello World',
          content: 'This is a new post about my hello world experience.',
          likes: 10,
          comments: 5,
          retweets: 3
        },
        {
          id: 2,
          username: 'janedoe',
          title: 'My React Journey',
          content: 'Today, I want to share my journey learning React. It’s been fun and challenging!',
          likes: 15,
          comments: 3,
          retweets: 5
        },
        {
          id: 3,
          username: 'tech_guru',
          title: 'Tips for Programming',
          content: 'Here are some tips I’ve learned during my 10 years as a programmer.',
          likes: 25,
          comments: 10,
          retweets: 2
        }
        // More posts...
      ];

//   useEffect(() => {
//     // Define the async function inside the effect
//     const fetchPosts = async () => {
//       try {
//         const response = await fetch('/api/posts'); // Replace with your API endpoint
//         const data = await response.json();
//         setPosts(data);
//       } catch (error) {
//         console.error('Error fetching posts:', error);
//       }
//     };

//     // Call the function
//     fetchPosts();
//   }, []); // Empty dependency array means this effect runs once on mount

  return (
    <div>
      {posts.map((post) => (
        <Post key={post.id} {...post} />
      ))}
    </div>
  );
};

export default PostList;
