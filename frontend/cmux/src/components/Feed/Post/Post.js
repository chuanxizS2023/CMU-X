import React, {useEffect} from "react";
import { Avatar } from "@material-ui/core";
import "./Post.css";
import FavoriteIcon from "../../icons/FavoriteIcon";
import CommentIcon from "../../icons/CommentIcon";
import RetweetIcon from "../../icons/RetweetIcon";
import SharePostIcon from "../../icons/SharePostIcon";
import MoreHorizIcon from "@material-ui/icons/MoreHoriz";
import { MillToDate } from "../../../utils/MillToDate";
import {StompClientSingleton} from "../../../socketClient";
import {addLike, deletePost} from '../../../apis/communitypostAPIs/postAPI'
import ProfileCard from "../../ProfileCard/ProfileCard";
import DeleteIcon from "@material-ui/icons/Delete";

function Post({communityPostid, userImage, username, title, content, likes, comments, retweets, onCommentClick, commentsCount, created_Date  }) {
  const [isVisibleProfileCard, setIsVisibleProfileCard] = React.useState(false);
  useEffect(() => {
    console.log("communityPostid: ", communityPostid);
  }, [communityPostid]);
  const onLikeClick = async (communityPostid) => {
    console.log("like clicked with id: ", communityPostid);
    const res = await addLike(communityPostid);
  }
  const onDeleteClick = async () => {
    console.log("delete clicked with id: ", communityPostid);
    const res = await deletePost(communityPostid);
  }
  
  return (
    <div className="post" onMouseLeave={() => setIsVisibleProfileCard(false)}>
      <ProfileCard active={isVisibleProfileCard && true} />
      <div>
        <Avatar src={userImage} />
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
            {username}
          </span>
          <span className="post-header-date">{MillToDate(created_Date)}</span>
          <MoreHorizIcon className="postMoreIcon" />
        </div>
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
            <DeleteIcon className="deleteIcon" onClick={onDeleteClick} /> 
          </div>
          <div>
            <SharePostIcon className="postIcon" />
          </div>
        </div>
      </div>
    </div>
  );
}

export default Post;
