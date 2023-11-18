// src/pages/CommunityPage.js
import React, { useState, useEffect } from 'react';
import Header from '../../components/common/header';
import PostList from '../../containers/communityPost/postContainer';
import { getPostById } from '../../apis/communitypostAPIs/postAPI'; // Adjust the import path as necessary

const CommunityPage = () => {
  const [post, setPost] = useState(null);
  const postId = 1; // Replace with the actual post ID you want to fetch

  useEffect(() => {
    const fetchPost = async () => {
      try {
        const data = await getPostById(postId);
        setPost(data);
      } catch (error) {
        console.error('Failed to fetch post:', error);
      }
    };

    fetchPost();
  }, [postId]);

  const styles = {
    page: {
      maxWidth: '600px',
      margin: '0 auto',
    },
  };

  return (
    <div style={styles.page}>
      <Header title="CMU-X Community" />
      {post && <PostList posts={[post]} />}
      <div>Hello world</div>
    </div>
  );
};

export default CommunityPage;
