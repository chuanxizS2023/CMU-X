import React from 'react';
import { Dialog, DialogTitle, List } from '@mui/material';
import Post from '../../Feed/Post/Post';

function SearchResultsPopup({ open, onClose, searchResults }) {
    
  return (
    <Dialog open={open} onClose={onClose} fullWidth maxWidth="sm"  PaperProps={{
        style: { backgroundColor: '#15202b', opacity: 1 } // Change as per your color preference
      }}>
      <DialogTitle color="white">Search Results</DialogTitle>
      <List>
        {searchResults.map((post) => (
            <Post
              key={post.communityPostid}
              communityPostid={post.communityPostid}
              username={post.author_id}
              userimage={post.userimage}
              created_Date={post.created_Date}
              title={post.title}
              content={post.content}
              likes={post.likes}
              comments={post.comments}
              retweets={post.retweets}
              commentsCount={post.commentsCount}
              findTeammatePost={post.findTeammatePost}
              instructorName={post.instructorName}
              courseNumber={post.courseNumber}
              semester={post.semester}
              teamMembers={post.teamMembers}
            />
        ))}
      </List>
    </Dialog>
  );
}

export default SearchResultsPopup;
