// src/pages/CommunityPage.js
import React, { useState, useEffect, useRef } from 'react';
import Header from '../../components/common/header';
import PostList from '../../containers/communityPost/postContainer';
import { getPostById, getAllPosts, createPost } from '../../apis/communitypostAPIs/postAPI'; // Adjust the import path as necessary
import TopBar from '../../components/common/topNavbar';
import BottomBar from '../../components/common/botNavbar';
import AddPostFab from '../../components/communityPost/addPostFab';
import { Container } from '@mui/material';
import PostForm from '../../components/communityPost/postForm';
import CommonPopup from '../../components/common/popup';
import CommentForm from '../../components/communityPost/commentForm';
import { saveComment, getComment } from '../../apis/communitypostAPIs/commentAPI';
import { subscribeToTopic, client } from '../../socketClient';

const CommunityPage = () => {
  const [author_id, setAuthor_id] = useState(1);
  const [post, setPost] = useState([]);
  const [popupContext, setPopupContext] = useState('');
  const [openPopup, setOpenPopup] = useState(false);
  const [postId, setPostId] = useState(1);
  const [isPostFormOpen, setPostFormOpen] = useState(false);
  const [isCommentFormOpen, setCommentFormOpen] = useState(false);
  const [activeCommentPostId, setActiveCommentPostId] = useState(null);
  const hasSubscribed = useRef(false);
  const delay = ms => new Promise(res => setTimeout(res, ms));

  useEffect(() => {
    if (!hasSubscribed.current) {
    const initiateSubscription = async () => {
      await delay(1000); 
      hasSubscribed.current = true;
      const postUpdateSubscribe = subscribeToTopic('/topic/post-update', (message) => {
        console.log("post-update received: ")
        const { communityPostid, title, content, likes, comments } = message;
        setPost(prevPosts => {
          const existingPostIndex = prevPosts.findIndex(post => post.communityPostid === communityPostid);
          if (existingPostIndex !== -1) {
            // Update the existing post
            const updatedPosts = [...prevPosts];
            updatedPosts[existingPostIndex] = message;
            return updatedPosts;
          } else {
            // Add new post if it doesn't exist
            return [...prevPosts, { postId: communityPostid, title, content, likes, comments }];
          }
        });
      });

      const postDeleteSubscribe = subscribeToTopic('/topic/post-delete', (postId) => {
        setPost(prevPosts => prevPosts.filter(post => post.communityPostid !== postId));
      });

      // const commentCreateSubscribe = subscribeToTopic('/topic/comment-create', (message) => {
      //   const { communityPostid, commentid, content, created_Date, author_id, likes } = message;
      //   setPost(prevPosts => {
      //     const existingPostIndex = prevPosts.findIndex(post => post.communityPostid === communityPostid);
      //     if(existingPostIndex !== -1){
      //       const updatedPosts = [...prevPosts];
      //       const newComment = {
      //         commentid,
      //         content,
      //         created_Date,
      //         author_id,
      //         likes
      //       };
      //       updatedPosts[existingPostIndex].comments = updatedPosts[existingPostIndex].comments ? [...updatedPosts[existingPostIndex].comments, newComment] : [newComment];
      //       return updatedPosts
      //     }else{
      //       return prevPosts;
      //     }
      //   }); 
      // });
      
    };
    initiateSubscription();
  }
  setAuthor_id(3);
  }, []);
  
  useEffect(() => {
    console.log("post: ", post);
  }, [post]);

  const handleAddPostClick = () => {
    setPostFormOpen(true);
  };
  const handleCommentFormOpen = (postId) => {
    setActiveCommentPostId(postId);
    setCommentFormOpen(true);
  };
  
  const handlepopUpOpen = () => setOpenPopup(true);
  const handlepopUpClose = () => setOpenPopup(false);

  // Function to close the post creation form
  const handleClosePostForm = () => {
    setPostFormOpen(false);
  };
  const handleCloseCommentForm = () => {
    setCommentFormOpen(false);
    setActiveCommentPostId(null); // Reset the active post id
  };
  const handlePostSubmit = (postData) => {
    // Logic to submit the post data to the backend
    postData.author_id = author_id;
    postData.created = new Date().toLocaleString();
    const response = createPost(postData);
    if (response) {
      setPopupContext('Post created successfully!');
      handlepopUpOpen();
    } else {
      setPopupContext('Failed to create post!');
      handlepopUpOpen();
    }
  };
  const handleCommentSubmit = async (commentData) => {
    // Logic to submit the post data to the backend
    commentData.author_id = author_id;
    commentData.communityPostid = activeCommentPostId;
    
    const response = await saveComment(commentData);
    console.log("status: ", response.status)
    if (response.status === 200) {
      setPost(prevPosts => {
        const existingPostIndex = prevPosts.findIndex(post => post.communityPostid === activeCommentPostId);
        if(existingPostIndex !== -1){
          const updatedPosts = [...prevPosts];
          updatedPosts[existingPostIndex].commentsCount += 1;
          if (updatedPosts[existingPostIndex].comments) {
            updatedPosts[existingPostIndex].comments.push(commentData);
          }else{
            updatedPosts[existingPostIndex].comments = [commentData];
          }
          return updatedPosts
        }else{ 
          return prevPosts;
        }
      });
      setPopupContext('Comment created successfully!');
      handlepopUpOpen();
    } else {
      setPopupContext('Failed to create comment!');
      handlepopUpOpen();
    }
  }

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
        {post && <PostList posts={post} onCommentClick={handleCommentFormOpen} />}
        <button onClick={()=>{setPostId(postId+1);fetchPost()}}>Fetch Post</button>
      </Container>
      <AddPostFab onClick={handleAddPostClick} />
      <BottomBar />

      {/* popup */}
      <PostForm open={isPostFormOpen} onClose={handleClosePostForm} onSubmit={handlePostSubmit} />
      <CommonPopup
        isOpen={openPopup}
        handleClose={handlepopUpClose}
        text={popupContext}
      />
      <CommentForm
        open={isCommentFormOpen}
        onClose={handleCloseCommentForm}
        onSubmit={handleCommentSubmit}
      />

    </>
  );
};

export default CommunityPage;
