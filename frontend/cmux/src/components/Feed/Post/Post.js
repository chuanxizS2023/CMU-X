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

function Post({communityPostid, userImage, username, title, content, likes, comments, retweets, onCommentClick, onPostClick, commentsCount, created_Date,findTeammatePost,instructorName, courseNumber, semester, teamMembers  }) {
  const [isVisibleProfileCard, setIsVisibleProfileCard] = React.useState(false);

  const onLikeClick = async (e, communityPostid) => {
    e.stopPropagation();
    const res = await addLike(communityPostid);
  }
  const onDeleteClick = async (e) => {
    e.stopPropagation();
    const res = await deletePost(communityPostid);
  }
  
  return (
    <div className="post" onMouseLeave={() => setIsVisibleProfileCard(false)} onClick={() => {onPostClick(communityPostid)}}>
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
          <span className="post-header-date">{created_Date}</span>
          <MoreHorizIcon className="postMoreIcon" />
        </div>
        <div style={{fontWeight:"bolder", fontSize:"25px", justifyContent:"center"}}>{title}</div>
        <div className="post-content" style={{color:"white"}}>{content}</div>
        {findTeammatePost && (
          <div style={{marginTop:"10px", color:"white"}}>
            <div>This is a FindTeammate Post</div>
            <div className="post-findTeammate-section" style={{ border:"1px solid black"}}>
              <div style={{display:"flex", justifyContent:"space-evenly"}}>
                <div className="post-findTeammate-course">
                  <span>Course: </span>
                  <span>{courseNumber}</span>
                </div>
                <div className="post-findTeammate-semester">
                  <span>Semester: </span>
                  <span>{semester}</span>
                </div>
              </div>
                <div className="post-findTeammate-teamMembers" style={{display:"flex", justifyContent:"center"}}>
                  <span>Team Members: </span>
                  <span>{teamMembers}</span>
                </div>
                <div className="post-findTeammate-instructor" style={{display:"flex", justifyContent:"center"}}>
                  <span>Instructor: </span>
                  <span>{instructorName}</span>
                </div>
              </div>
          </div>
        )}
        {/* {shareImage && (
          <div className="post-image">
            <img src={shareImage} alt="shareimage" />
          </div>
        )} */}
        <div className="post-event">
          <div>
            <CommentIcon className="postIcon" onClick={(e) => onCommentClick(e, communityPostid)} />
            <span>{commentsCount}</span>
          </div>
          <div>
            <FavoriteIcon className="postIcon" onClick={(e)=>{onLikeClick(e, communityPostid)}}/>
            <span>{likes}</span>
          </div>
          <div>
            <DeleteIcon className="deleteIcon" onClick={(e)=>{onDeleteClick(e)}} /> 
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
