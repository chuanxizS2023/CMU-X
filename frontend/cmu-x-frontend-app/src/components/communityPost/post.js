import React from 'react';

const Post = ({username, title, content, likes, comments, retweets }) => {
  const styles = {
    postContainer: {
      display: 'flex',
      flexDirection: 'row',
      padding: '10px',
      borderBottom: '1px solid #ccc',
    },
    avatar: {
      width: '50px',
      height: '50px',
      borderRadius: '50%',
      marginRight: '10px',
    },
    postContent: {
      flex: 1,
    },
    engagement: {
      display: 'flex',
      justifyContent: 'space-between',
      marginTop: '10px',
    },
  };

  return (
    <div style={styles.postContainer}>
        <h4>{title}</h4>  
      <div style={styles.postContent}>
        <strong>{username}</strong>
        <p>{content}</p>
        <div style={styles.engagement}>
          <span>{likes} Likes</span>
          <span>{comments} Comments</span>
          <span>{retweets} Retweets</span>
        </div>
      </div>
    </div>
  );
};

export default Post;
