// src/pages/CommunityPage.js
import React, { useState, useEffect } from 'react';
import Header from '../../components/common/header';
import PostList from '../../containers/communityPost/postContainer';
import { getPostById, getAllPosts } from '../../apis/communitypostAPIs/postAPI'; // Adjust the import path as necessary
import TopBar from '../../components/common/topNavbar';
import BottomBar from '../../components/common/botNavbar';
import AddPostFab from '../../components/communityPost/addPostFab';
import { Container } from '@mui/material';
import PostForm from '../../components/communityPost/postForm';

const CommunityPage = () => {
  const [post, setPost] = useState([]);
  let postId = 2; // Replace with the actual post ID you want to fetch
  const [isPostFormOpen, setPostFormOpen] = useState(false);
  useEffect(()=>{
    fetchPost();
  },[])
  // Function to open the post creation form
  const handleAddPostClick = () => {
    setPostFormOpen(true);
  };

  // Function to close the post creation form
  const handleClosePostForm = () => {
    setPostFormOpen(false);
  };

  const handlePostSubmit = (postData) => {
    // Logic to submit the post data to the backend
    console.log(postData);
    // After submission, you might want to fetch the posts again to update the list
  };
    const fetchPost = async () => {
      try {
        const data = await getPostById(postId);
        setPost((prev) => [...prev, data]);
      } catch (error) {
        console.error('Failed to fetch post:', error);
      }
    };


  const styles = {
    page: {
      maxWidth: '600px',
      margin: '0 auto',
    },
  };

  return (
    <>
      <TopBar />
      <Container maxWidth="sm" style={{ marginBottom: 75 }}>
        <Header title="CMU-X Community" />
        {post && <PostList posts={post} />}
        <button onClick={()=>{postId++;fetchPost()}}>Fetch Post</button>
      </Container>
      <AddPostFab onClick={handleAddPostClick} />
      <PostForm open={isPostFormOpen} onClose={handleClosePostForm} onSubmit={handlePostSubmit} />
      <BottomBar />
    </>
  );
};

export default CommunityPage;
