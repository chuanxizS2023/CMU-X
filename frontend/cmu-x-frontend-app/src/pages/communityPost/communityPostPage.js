import React from 'react';
import Header from '../../components/common/header';
import PostList from '../../containers/communityPost/postContainer';

const CommunityPage = ({ posts }) => {
  const styles = {
    page: {
      maxWidth: '600px',
      margin: '0 auto',
    },
  };

  return (
    <div style={styles.page}>
      <Header title="CMU-X Community" />
      <PostList posts={posts} />
    </div>
  );
};

export default CommunityPage;
