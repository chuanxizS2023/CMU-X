import React, {useEffect, useState} from "react";
import { Dialog, DialogContent, DialogTitle, TextField, DialogActions, Button } from '@mui/material';
import { Avatar } from "@material-ui/core";
import "./Post.css";
import FavoriteIcon from "../../icons/FavoriteIcon";
import CommentIcon from "../../icons/CommentIcon";
import RetweetIcon from "../../icons/RetweetIcon";
import SharePostIcon from "../../icons/SharePostIcon";
import MoreHorizIcon from "@material-ui/icons/MoreHoriz";
import { MillToDate } from "../../../utils/MillToDate";
import {StompClientSingleton} from "../../../socketClient";
import {addLike, deletePost, getPostById} from '../../../apis/communitypostAPIs/postAPI'
import ProfileCard from "../../ProfileCard/ProfileCard";
import DeleteIcon from "@material-ui/icons/Delete";

// communityPostid, userImage, username, title, content, likes, comments, retweets, onCommentClick, commentsCount, created_Date  
function Comments({comment}) {
  const [isVisibleProfileCard, setIsVisibleProfileCard] = useState(false);
  const [author_id, setAuthor_id] = useState(null);
  const [content, setContent] = useState(null);
    const [created_Date, setCreated_Date] = useState(null);
    useEffect(() => {
        setAuthor_id(comment.author_id);
        setContent(comment.content);
        setCreated_Date(comment.created_Date);
    }, [comment]);
  
  return (
    <div className="post" onMouseLeave={() => setIsVisibleProfileCard(false)} style={{minWidth:"25vw"}}>
        <ProfileCard active={isVisibleProfileCard && true} />
        <div>
            <Avatar/>
        </div>
        <div className="post-content-col">
            <div className="post-header">
            <span
                className="post-header-displayname"
                style={{color:"black"}}
                onMouseEnter={() => setIsVisibleProfileCard(true)}
                onMouseLeave={() => {
                setTimeout(function () {
                    setIsVisibleProfileCard(false);
                }, 1000);
                }}
            >
                {author_id}
            </span>
            <span className="post-header-date">{created_Date}</span>
            <MoreHorizIcon className="postMoreIcon" />
            </div>
            <div className="post-content" style={{color:"black"}}>{content}</div>
            <div className="post-event">
            <div>
                <SharePostIcon className="postIcon" />
            </div>
            </div>
        </div>
    </div>
  );
}

export default Comments;
