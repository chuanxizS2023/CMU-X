import { Avatar } from "@material-ui/core";
import React from "react";
import axios from 'axios';
import { useState, useEffect } from 'react';
import { VerifiedIcon } from "../icons";
import "./ProfileCard.css";
import { AuthContext } from "../AuthProvider";
import { useFetchWithTokenRefresh } from '../../utils/ApiUtils';

const ProfileCard = ({ active, username, userId, initialIsFollowing, following, followers }) => {
  const [ isFollowing, setIsFollowing ] = useState(initialIsFollowing);
  const [ isVisible, setIsVisible ] = useState(false);
  // Mock userId, should be replaced by the real userId
  const myuserId = 1;
  // const userId = useContext(AuthContext).userId;

  const [ isSelf, setIsSelf ] = useState(userId === myuserId);
  const baseURL = 'http://localhost:8082';
  const subscription_url = `${baseURL}/subscriptions`;
  useEffect(() => {
    setIsSelf(userId === myuserId);
  }, [ userId, myuserId ]);

  const updateFollowers = async (method, query) => {
    try {
      const response = await axios({
        method: method,
        url: `${subscription_url}?${query}`,
      });
      if (response.status === 200) {
        setIsFollowing(!isFollowing);

      }
    } catch (error) {
      console.error("Error during update:", error);
    }
  };

  const handleFollowClick = () => {
    const query = `userId=${myuserId}&otherUserId=${userId}`;
    updateFollowers('put', query); // Use 'put' for follow
  };

  const handleUnfollowClick = () => {
    const query = `userId=${myuserId}&otherUserId=${userId}`;
    updateFollowers('delete', query); // Use 'delete' for unfollow
  };

  return (
    <div className={ active || isVisible ? "profileDetailCard" : "profileDetailCardActive" }>
      <div>
        <Avatar src="" />
        {
          isSelf ? null : (<div style={ { cursor: 'pointer' } }>
            <span onClick={ isFollowing ? handleUnfollowClick : handleFollowClick }>
              { (isFollowing ? "Unfollowing" : "Follow") }
            </span>
          </div>)
        }
      </div>
      <div>
        <span>{ username }</span>
      </div>
      <div>
        <span>{ userId }</span>
      </div>
      <div>
        <span>Student</span>
      </div>
      <div>
        <span >
          <span>{ following }</span>
          <span>Following</span>
        </span>
        <span>
          <span>{ followers }</span>
          <span>Followers</span>
        </span>
      </div>
    </div>
  );
};

export default ProfileCard;
