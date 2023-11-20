// src/pages/CommunityPage.js
import React, { useState, useEffect } from 'react';
import Header from '../../components/common/header';
import PostList from '../../containers/communityPost/postContainer';
import { getPostById } from '../../apis/communitypostAPIs/postAPI'; // Adjust the import path as necessary
import TopBar from '../../components/common/topNavbar';
import BottomBar from '../../components/common/botNavbar';
import AddPostFab from '../../components/communityPost/addPostFab';
import { Container } from '@mui/material';

const CommunityPage = () => {
  const [post, setPost] = useState([]);
  const postId = 2; // Replace with the actual post ID you want to fetch

    const fetchPost = async () => {
      try {
        const data = await getPostById(postId);
        setPost(data);
      } catch (error) {
        console.error('Failed to fetch post:', error);
      }
    };

  const fetchAllPosts = async () => {
    try {
      let data = await getPostById(postId);
      while (data){
        post.push(data);
        postId++;
        data = await getPostById(postId);
      }
      setPost(post);
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
        {post && <PostList posts={[post]} />}
      </Container>
      <AddPostFab/>
      <BottomBar />
    </>
  );
};

export default CommunityPage;
