import React, {useEffect, useState} from "react";
import "./Feed.css";
import TweetBox from "./TweetBox/TweetBox";
import CommonPopup from "./Post/popup"
import Post from "./Post/Post";
import HomeStars from "../icons/HomeStars";
import BottomSidebar from "../BottomSidebar/BottomSidebar";
import { useSelector } from "react-redux";
import { Avatar } from "@material-ui/core";
import DrawerBar from "../DrawerBar/DrawerBar";
import Loading from "../Loading/Loading";
import logo from "../../assets/cmux_logo_no_bg.png";
import {stompClientInstance} from "../../socketClient";
import {createPost} from "../../apis/communitypostAPIs/postAPI";
import PostForm from "./Post/postForm";
import { fetchPostsByIds } from "../../data/postData";


function Feed() {
  const [isDrawerBar, setIsDrawerBar] = React.useState(false);
  const [loading, setLoading] = React.useState(true);
  const [isPostFormOpen, setPostFormOpen] = React.useState(false);
  const [author_id, setAuthor_id] = React.useState(1);
  const [popupContext, setPopupContext] = useState('');
  const [openPopup, setOpenPopup] = useState(false);

  const [posts, setPosts] = useState([]);
  const delay = ms => new Promise(res => setTimeout(res, ms));
  const handlepopUpOpen = () => setOpenPopup(true);
  const handlepopUpClose = () => setOpenPopup(false);
  const handleClosePostForm = () => setPostFormOpen(false);

  const handlePostSubmit = (postData) => {
    // Logic to submit the post data to the backend
    postData.author_id = author_id;
    postData.created = new Date().toLocaleString();
    const response = createPost(postData);
    if (response) {
      setPopupContext('Post created successfully!');
      handlepopUpOpen();
    } else {
      setPopupContext('Failed to create post!');
      handlepopUpOpen();
    }
  };

  setTimeout(() => {
    setLoading(false);
  }, 500);


  useEffect(() => {
    const postIds = [1]
    const establishConnection = async () => {
      await delay(2000);
      await stompClientInstance.ensureConnection();
       stompClientInstance.subscribeToTopic('/topic/post-update', (message) => {
          console.log("post-update received: ")
          const { communityPostid, title, content, likes, comments } = message;
          setPosts(prevPosts => {
            const existingPostIndex = prevPosts.findIndex(post => post.communityPostid === communityPostid);
            if (existingPostIndex !== -1) {
              // Update the existing post
              const updatedPosts = [...prevPosts];
              updatedPosts[existingPostIndex] = message;
              return updatedPosts;
            } else {
              // Add new post if it doesn't exist
              return [...prevPosts, { postId: communityPostid, title, content, likes, comments }];
            }
          });
        });
      stompClientInstance.subscribeToTopic('/topic/post-delete', (postId) => {
          setPosts(prevPosts => prevPosts.filter(post => post.communityPostid !== postId));
      });
      }


    fetchPostsByIds(postIds)
      .then(fetchedPosts => {
        setPosts(fetchedPosts);
        setLoading(false);
      })
      .catch(error => console.error('Failed to fetch posts', error));
    establishConnection();

  }, []);

  useEffect(() => {
    console.log("debug posts: ", posts);
  }
  , [posts]);

  return (
    <section className="feed">
      {isDrawerBar && (
        <div onClick={() => setIsDrawerBar(false)} className="drawerBarPanel" />
      )}
      <DrawerBar active={isDrawerBar} />
      <div className="feed-header">
        <div onClick={() => setIsDrawerBar(true)}>
          <Avatar src={logo} />
        </div>
        <div className="feed-headerText">
          <span>Home</span>
        </div>
        <div className="homeStarsCol">
          <HomeStars className="homeStars" width={22} height={22} />
        </div>
      </div>
      <TweetBox setPostFormOpen={setPostFormOpen}/>
      {loading ? (
        <Loading />
      ) : (
        <article>
          {posts.map((post) => (
            <Post
              key={post.communityPostid}
              communityPostid={post.communityPostid}
              username={post.username}
              userimage={post.userimage}
              date={post.created_Date}
              title={post.title}
              content={post.content}
              likes={post.likes}
              comments={post.comments}
              retweets={post.retweets}
              commentsCount={post.commentsCount}
              onCommentClick={() => {}}
            />
          ))}
        </article>
      )}
      <PostForm open={isPostFormOpen} onClose={handleClosePostForm} onSubmit={handlePostSubmit} />
      <CommonPopup
        isOpen={openPopup}
        handleClose={handlepopUpClose}
        text={popupContext}
      />
      <BottomSidebar />
    </section>
  );
}

export default Feed;
