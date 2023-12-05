import { Avatar } from "@material-ui/core";
import React from "react";
import axios from 'axios';
 
import { VerifiedIcon } from "../icons";
import "./ProfileCard.css";
import { AuthContext } from "../AuthProvider";
import {useFetchWithTokenRefresh} from '../../utils/ApiUtils';

const ProfileCard = ({ active, username, userId, isFollowing, setIsFollowing, following, followers, isSelf}) => {
  const [isVisible, setIsVisible] = React.useState(false);
  // Mock userId, should be replaced by the real userId
  // const myuserId = useContext(AuthContext).userId;
  const myuserId = '1';
  //========================================
  const baseURL = 'http://localhost:8082';
  const subscription_url = baseURL + '/subscriptions';
  const addFollowers = async (query) => {
    try {
      const response = await axios.put(`${subscription_url}?${query}`);
      return response.data;
    } catch (error) {
      console.error("Error during search:", error);
      return;
    }
  };
  const removeFollowers = async (query) => {
    try {
      const response = await axios.delete(`${subscription_url}?${query}`);
      return response.data;
    } catch (error) {
      console.error("Error during search:", error);
      return;
    }
  };
  const handleFollowClick = async () => {
    console.log("follow");
    const query = `userId=${myuserId}&otherUserId=${userId}`;
    const response = await addFollowers(query);
    //if success, change isFollowing to true
    setIsFollowing(true);
  };
  const handleUnfollowClick = () => {
    console.log("unfollow");
    const query = `userId=${myuserId}&otherUserId=${userId}`;
    const response = removeFollowers(query);
    //if success, change isFollowing to false
    setIsFollowing(false);
  };

  return (
    <div
      className={
        active || isVisible ? "profileDetailCard" : "profileDetailCardActive"
      }
    >
      <div>
        <Avatar src="" />
        <div style={{cursor:"pointer"}}>
          <span onClick={isFollowing ? handleUnfollowClick : handleFollowClick}>{isSelf?null:(isFollowing?"Following":"Follow")}</span>
        </div>
      </div>
      <div>
        <span>{username}</span>
      </div>
      <div>
        <span>{userId}</span>
      </div>
      <div>
        <span>Student</span>
      </div>
      <div>
        <span >
          <span>{following}</span>
          <span>Following</span>
        </span>
        <span>
          <span>{followers}</span>
          <span>Followers</span>
        </span>
      </div>
    </div>
  );
};

export default ProfileCard;
