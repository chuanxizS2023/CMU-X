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
function SinlgePost({open, onClose, communityPostid}) {
  const [isVisibleProfileCard, setIsVisibleProfileCard] = useState(false);
  const [author_id, setAuthor_id] = useState(null);
  const [title, setTitle] = useState(null);
    const [content, setContent] = useState(null);
    const [likes, setLikes] = useState(null);
    const [comments, setComments] = useState(null);
    const [commentsCount, setCommentsCount] = useState(null);
    const [created_Date, setCreated_Date] = useState(null);
  useEffect(() => {
    const fetchPost = async () => {
        try{
            const res = await getPostById(communityPostid);
            if(!res) return console.log("no post found");
            setAuthor_id(res.author_id);
            setTitle(res.title);
            setContent(res.content);
            setLikes(res.likes);
            setComments(res.comments);
            setCommentsCount(res.commentsCount);
            setCreated_Date(res.created_Date);
        }catch(err){
            console.log(err);
        }
    }
    fetchPost();
  }, []);
  const onLikeClick = async (communityPostid) => {
    console.log("like clicked with id: ", communityPostid);
    const res = await addLike(communityPostid);
  }
  const onDeleteClick = async () => {
    console.log("delete clicked with id: ", communityPostid);
    const res = await deletePost(communityPostid);
  }
  
  return (
    <Dialog open={open} onClose={onClose} >
    <div className="post-with-comment" style={{width:"25vw", maxWidth:"30vw"}}>
        <div className="post" onMouseLeave={() => setIsVisibleProfileCard(false)}>
            <ProfileCard active={isVisibleProfileCard && true} />
            <div>
                <Avatar/>
            </div>
            <div className="post-content-col">
                <div className="post-header">
                <span
                    className="post-header-displayname"
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
                <div style={{fontWeight:"bolder", fontSize:"25px", justifyContent:"center"}}>{title}</div>
                <div className="post-content">{content}</div>
                {/* {shareImage && (
                <div className="post-image">
                    <img src={shareImage} alt="shareimage" />
                </div>
                )} */}
                <div className="post-event">
                <div>
                    <CommentIcon className="postIcon" />
                    <span>{commentsCount}</span>
                </div>
                <div>
                    <FavoriteIcon className="postIcon" onClick={()=>{onLikeClick(communityPostid)}}/>
                    <span>{likes}</span>
                </div>
                <div>
                    <SharePostIcon className="postIcon" />
                </div>
                </div>
            </div>
        </div>
    </div>
    </Dialog>
  );
}

export default SinlgePost;
