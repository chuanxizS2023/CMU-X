// src/pages/CommunityPage.js
import React, { useState, useEffect } from 'react';
import Header from '../../components/common/header';
import PostList from '../../containers/communityPost/postContainer';
import { getPostById, getAllPosts, createPost } from '../../apis/communitypostAPIs/postAPI'; // Adjust the import path as necessary
import TopBar from '../../components/common/topNavbar';
import BottomBar from '../../components/common/botNavbar';
import AddPostFab from '../../components/communityPost/addPostFab';
import { Container } from '@mui/material';
import PostForm from '../../components/communityPost/postForm';
import CommonPopup from '../../components/common/popup';

const CommunityPage = () => {
  const [post, setPost] = useState([]);
  const [popupContext, setPopupContext] = useState('');
  const [openPopup, setOpenPopup] = useState(false);
  const [postId, setPostId] = useState(1);
  const [isPostFormOpen, setPostFormOpen] = useState(false);
  // Function to open the post creation form

  const handleAddPostClick = () => {
    setPostFormOpen(true);
  };
  
  const handlepopUpOpen = () => setOpenPopup(true);
  const handlepopUpClose = () => setOpenPopup(false);

  // Function to close the post creation form
  const handleClosePostForm = () => {
    setPostFormOpen(false);
  };

  const handlePostSubmit = (postData) => {
    // Logic to submit the post data to the backend
    postData.author_id = 1;
    postData.created = new Date().toLocaleString();
    const response = createPost(postData);
    console.log("response", response)
    if (response) {
      setPopupContext('Post created successfully!');
      handlepopUpOpen();
    } else {
      setPopupContext('Failed to create post!');
      handlepopUpOpen();
    }

};

    const fetchPost = async () => {
      try {
        console.log("in fetchPost")
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
        <button onClick={()=>{setPostId(postId+1);fetchPost()}}>Fetch Post</button>
      </Container>
      <AddPostFab onClick={handleAddPostClick} />
      <PostForm open={isPostFormOpen} onClose={handleClosePostForm} onSubmit={handlePostSubmit} />
      <BottomBar />
      <CommonPopup
        isOpen={openPopup}
        handleClose={handlepopUpClose}
        text={popupContext}
      />
    </>
  );
};

export default CommunityPage;
