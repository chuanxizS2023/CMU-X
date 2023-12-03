import axios from 'axios';
import { sendMessage, subscribeToTopic } from '../../socketClient';

const baseUrl = 'http://localhost:9000/community';

// Create a new post
export const createPost = async (postData) => {
  try {
    const response = await axios.post(baseUrl, postData);
    // sendMessage('/app/communityPost.createPost', postData);
    return response;
  } catch (error) {
    console.error("Error creating post:", error.message);
    throw error;
  }
};

// Add a like to a post
export const addLike = async (postId) => {
  try {
    const response = await axios.post(`${baseUrl}/likes/${postId}`);
    return response;
  } catch (error) {
    console.error("Error adding like:", error.message);
    throw error;
  }
};

// Get a post by ID
export const getPostById = async (postId) => {
  try {
    const response = await axios.get(`${baseUrl}/${postId}`);
    return response.data;
  } catch (error) {
    console.error("Error getting post:", error.message);
    throw error;
  }
};

// Delete a post by ID
export const deletePost = async (postId) => {
  try {
    const response = await axios.delete(`${baseUrl}/${postId}`);
    return response.data;
  } catch (error) {
    console.error("Error deleting post:", error.message);
    throw error;
  }
};

// Update a post by ID
export const updatePost = async (postId, updateData) => {
  try {
    const response = await axios.put(`${baseUrl}/${postId}`, updateData);
    return response.data;
  } catch (error) {
    console.error("Error updating post:", error.message);
    throw error;
  }
};

// Mark a post as looking for teammates
export const markAsFindTeammatePost = async (postId, updateData) => {
  try {
    const response = await axios.post(`${baseUrl}/find-teammate/${postId}`, updateData);
    return response.data;
  } catch (error) {
    console.error("Error marking post for teammates:", error.message);
    throw error;
  }
};

// Add a team member to a post
export const addTeamMembers = async (postId, username) => {
  try {
    const response = await axios.put(`${baseUrl}/${postId}/team-members`, { username });
    return response.data;
  } catch (error) {
    console.error("Error adding team member:", error.message);
    throw error;
  }
};
