import React, { useContext, useEffect, useState } from "react";
import "./Profile.css";
import BottomSidebar from "../../components/BottomSidebar/BottomSidebar";
import FriendSuggestions from "../../components/Widgets/FriendSuggestions/FriendSuggestions";
import Topics from "../../components/Widgets/Topics/Topics";
import SearchInput from "../../components/Widgets/SearchInput/SearchInput";
import Post from "../../components/Feed/Post/Post";

import BackIcon from "@material-ui/icons/KeyboardBackspace";
import ScheduleIcon from "@material-ui/icons/CalendarToday";
import { Avatar } from "@material-ui/core";
import { useSelector } from "react-redux";
import { useHistory } from "react-router-dom";
import Links from "../../components/Widgets/Links/Links";
import HomeBox from "../../components/HomeBox/HomeBox";
import Loading from "../../components/Loading/Loading";
import { AuthContext } from '../../components/AuthProvider';
import { useFetchWithTokenRefresh } from '../../utils/ApiUtils';

const Profile = () => {
  const [category, setCategory] = React.useState(1);
  const { posts } = useSelector((state) => state.posts);
  const { userId, username } = useContext(AuthContext);
  const [userProfile, setUserProfile] = useState(null);
  let history = useHistory();
  document.title = "Test User (@testuser) / Twitter";
  const [loading, setLoading] = React.useState(true);
  const [isEditMode, setIsEditMode] = useState(false);
  const [editedUsername, setEditedUsername] = useState('');
  const [selectedImage, setSelectedImage] = useState('');
  const [isAvatarModalOpen, setIsAvatarModalOpen] = useState(false);
  setTimeout(() => {
    setLoading(false);
  }, 2000);

  const fetchUserProfile = useFetchWithTokenRefresh(`${process.env.REACT_APP_URL}user/${userId}`, {
    method: 'GET'
  });

  const loadUserProfile = async () => {
    console.log('Fetching user profile');
    try {
      const response = await fetchUserProfile();
      console.log(response);
      if (response.ok) {
        const data = await response.json();
        console.log('User profile:', data);
        setUserProfile(data);
      } else {
        console.error('Failed to fetch user profile');
      }
    } catch (error) {
      console.error('Error fetching user profile:', error);
    }
  };

  useEffect(() => {
    loadUserProfile();
  }, [userId, username]);

  const fetchUpdateProfile = useFetchWithTokenRefresh(`${process.env.REACT_APP_URL}user/${userId}`, {
    method: 'PATCH',
    headers: {
      'Content-Type': 'application/json'
    },
    body: JSON.stringify({
      username: editedUsername,
      userImage: selectedImage
    })
  });
  const handleEditClick = () => {
    setIsEditMode(true);
    setEditedUsername(userProfile.username);
    setSelectedImage(userProfile.userImage);
  };

  const handleSaveClick = async () => {
    setIsEditMode(false);

    try {
      const response = await fetchUpdateProfile();
      if (response.ok) {
        loadUserProfile();
      } else {
        console.error('Failed to update user profile');
      }
    } catch (error) {
      console.error('Error updating user profile:', error);
    }
  };

  const handleAvatarClick = () => {
    if (isEditMode) {
      setIsAvatarModalOpen(true);
    }
  };

  const handleAvatarSelect = (image) => {
    setSelectedImage(image);
    setIsAvatarModalOpen(false);
  };


  return (
    <HomeBox>
      <section className="feed">
        <div className="profileHeader">
          <div onClick={() => history.goBack()}>
            <BackIcon />
          </div>
          <div>
            {userProfile ? (
              <>
                <span>{userProfile.username}</span>
                <span>12 Tweets</span>
              </>
            ) : (
              <span>Loading...</span>
            )}
          </div>
        </div>
        {
          userProfile && (
            <div className="profile">
              <div className="backgroundImage"></div>
              <div className="profileTitle">
                <div className="profileImage" onClick={ handleAvatarClick }>
                  <Avatar src={userProfile.userImage} />
                </div>
                {
                  isAvatarModalOpen && (
                    <div className="avatarModal">
                      {userProfile.unlockedImages.map((image) => (
                        <div key={image} className="avatarOption" onClick={() => handleAvatarSelect(image)}>
                          <Avatar src={image} className="selectableAvatar" />
                        </div>
                      ))}
                    </div>
                  )
                }
                <div className="editProfile" onClick={isEditMode ? handleSaveClick : handleEditClick}>
                  <span>{isEditMode ? 'Save' : 'Edit Profile'}</span>
                </div>
              </div>
              <div className="profileBiography">
                {isEditMode ? (
                  <input
                    value={editedUsername}
                    onChange={(e) => setEditedUsername(e.target.value)}
                  />
                ) : (
                  <span>{userProfile.username}</span>
                )}
                <span>@{userProfile.username}</span>
                <span>Junior Software Developer</span>
                <span>
                  <ScheduleIcon />
                  Joined December 2011
                </span>
              </div>
              <div>
                <span>
                  <span>167</span>
                  <span>Following</span>
                </span>
                <span>
                  <span>167</span>
                  <span>Followers</span>
                </span>
              </div>
              <div className="profileCategory">
                <div
                  className={category === 1 && "profileCategoryActive"}
                  onClick={() => setCategory(1)}
                >
                  <span>Tweets</span>
                </div>
                <div
                  className={category === 2 && "profileCategoryActive"}
                  onClick={() => setCategory(2)}
                >
                  <span>Tweets & replies</span>
                </div>
                <div
                  className={category === 3 && "profileCategoryActive"}
                  onClick={() => setCategory(3)}
                >
                  <span>Media</span>
                </div>
                <div
                  className={category === 4 && "profileCategoryActive"}
                  onClick={() => setCategory(4)}
                >
                  <span>Likes</span>
                </div>
              </div>
            </div>
          )}
        <article className="profilePosts">
          {!loading ? (
            posts.map((post) => (
              <Post
                key={post.id}
                username={post.username}
                userimage={post.userimage}
                date={post.date}
                displayName={post.displayName}
                text={post.text}
                shareImage={post.shareImage}
              />
            ))
          ) : (
            <Loading />
          )}
        </article>
        <BottomSidebar />
      </section>
      <div className="widgets">
        <SearchInput placeholder="Search Twitter" />
        <FriendSuggestions />
        <Topics />
        <Links />
      </div>
    </HomeBox>
  );
};

export default Profile;
