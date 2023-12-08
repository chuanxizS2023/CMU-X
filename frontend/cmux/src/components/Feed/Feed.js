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
import { saveComment } from "../../apis/communitypostAPIs/commentAPI";
import {createPost, fetchPostsByAuthorIds} from "../../apis/communitypostAPIs/postAPI";
import PostForm from "./Post/postForm";
import CommentForm from "./Post/commentForm"
import SinlgePost from "./Post/singlePost";
import { fetchPostsByIds } from "../../data/postData";


function Feed() {
  const [isDrawerBar, setIsDrawerBar] = React.useState(false);
  const [loading, setLoading] = React.useState(true);
  const [isPostFormOpen, setPostFormOpen] = React.useState(false);
  const [authorid, setAthorid] = React.useState(2);
  const [username, setUsername] = React.useState('testuser');
  const [isCommentFormOpen, setCommentFormOpen] = useState(false);
  const [activeCommentPostId, setActiveCommentPostId] = useState(null);
  const [activePostId, setActivePostId] = useState(null); 
  const [popupContext, setPopupContext] = useState('');
  const [openPopup, setOpenPopup] = useState(false);
  const [openSinglePost, setOpenSinglePost] = useState(false);
  const [posts, setPosts] = useState([]);
  const delay = ms => new Promise(res => setTimeout(res, ms));
  const handlepopUpOpen = () => setOpenPopup(true);
  const handlepopUpClose = () => setOpenPopup(false);
  const handleClosePostForm = () => setPostFormOpen(false);

  const handlePostSubmit = (postData) => {
    // Logic to submit the post data to the backend
    postData.authorid = authorid;
    postData.username = username;
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

  const handleCommentFormOpen = (e, postId) => {
    e.stopPropagation();
    setActiveCommentPostId(postId);
    setCommentFormOpen(true);
  };

  const handleCloseCommentForm = () => {
    setCommentFormOpen(false);
    setActiveCommentPostId(null); // Reset the active post id
  };

  const handleOpenSinglePost = (postId) => {
    setOpenSinglePost(true);
    setActivePostId(postId)
  };

  const handleCloseSinglePost = () => {
    setOpenSinglePost(false);
    setActivePostId(null);
  };



  const handleCommentSubmit = async (commentData) => {
    // Logic to submit the post data to the backend
    commentData.authorid = authorid;
    commentData.username = username;
    commentData.communityPostid = activeCommentPostId;
    
    const response = await saveComment(commentData);
    if (response.status === 200) {
      setPosts(prevPosts => {
        const existingPostIndex = prevPosts.findIndex(post => post.communityPostid === activeCommentPostId);
        if(existingPostIndex !== -1){
          const updatedPosts = [...prevPosts];
          updatedPosts[existingPostIndex].commentsCount += 1;
          if (updatedPosts[existingPostIndex].comments) {
            updatedPosts[existingPostIndex].comments.push(commentData);
          }else{
            updatedPosts[existingPostIndex].comments = [commentData];
          }
          return updatedPosts
        }else{ 
          return prevPosts;
        }
      });
      setPopupContext('Comment created successfully!');
      handlepopUpOpen();
    } else {
      setPopupContext('Failed to create comment!');
      handlepopUpOpen();
    }
  }

  setTimeout(() => {
    setLoading(false);
  }, 500);


  useEffect(() => {
    const postIds = [1,2,3,4,5,6,7]
    const authorid_list = [2]
    const establishConnection = async () => {
      await delay(2000);
      await stompClientInstance.ensureConnection();
       stompClientInstance.subscribeToTopic('/topic/post-update', (message) => {
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

      stompClientInstance.subscribeToTopic('/topic/post-created', (message)=>{
        setPosts(prevPost =>{
            return [...prevPost, message];
          })
      })
    }

    const fetchAndSetPosts = async () => {
      try {
        const allPosts = await fetchPostsByAuthorIds(); // Await the result of fetchPostsByIds
        setPosts(allPosts);
      } catch (error) {
        console.error('Error fetching posts:', error);
        // Handle error, for example, set an error state or a message to the user
      }
    };

    const fetchPostFromAuthoridList = async (authorid_list) => {
      try {
        const allPosts = await fetchPostsByAuthorIds(authorid_list); 
        setPosts(allPosts);
      } catch (error) {
        console.error('Error fetching posts:', error);
        // Handle error, for example, set an error state or a message to the user
      }
    }
    fetchPostFromAuthoridList(authorid_list);
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
              onCommentClick={handleCommentFormOpen}
              onPostClick={handleOpenSinglePost}
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
      <CommentForm
        open={isCommentFormOpen}
        onClose={handleCloseCommentForm}
        onSubmit={handleCommentSubmit}
      />
      <BottomSidebar />
      {openSinglePost && (
        <SinlgePost 
          open={openSinglePost}
          onClose={handleCloseSinglePost}
          communityPostid={activePostId}
        />
      )}
    </section>
  );
}

export default Feed;
