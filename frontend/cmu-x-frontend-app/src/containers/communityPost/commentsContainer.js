import React, { useEffect } from 'react';
import Card from '@mui/material/Card';
import CardContent from '@mui/material/CardContent';
import Typography from '@mui/material/Typography';
import Avatar from '@mui/material/Avatar';
import { formatDistanceToNow } from 'date-fns';

const Comment = ({ content, author, create_Date }) => {
    let author_avatar;
    if(author[0]){
        console.log("author: ", author)
        author_avatar = author[0].toUpperCase();
    }else{
        author_avatar = null;
    }
    return(
        <Card sx={{ marginBottom: 2 }}>
            <CardContent sx={{ display: 'flex', alignItems: 'center' }}>
            <Avatar sx={{ marginRight: 2 }}>{author_avatar}</Avatar>
            <div>
                <Typography variant="subtitle2">{author}</Typography>
                <Typography variant="body2" color="text.secondary">
                {/* {formatDistanceToNow(new Date(create_Date), { addSuffix: true })} */}
                </Typography>
                <Typography variant="body1">{content}</Typography>
            </div>
            </CardContent>
        </Card>
    )
};

const CommentContainer = ({ comments }) => (
  <div>
    {comments.map((comment, index) => (
      <Comment
        key={index}
        content={comment.content}
        author={comment.author_id}
        createDate={comment.create_Date}
      />
    ))}
  </div>
);

export default CommentContainer;
