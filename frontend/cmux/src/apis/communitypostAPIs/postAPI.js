import axios from 'axios';
import { StompClientSingleton } from '../../../../cmux/src/socketClient';


const baseUrl = process.env.REACT_APP_POST_URL || "";

// Create a new post
export const createPost = async (postData) => {
  try {
    const response = await axios.post(baseUrl, postData);
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

export const fetchPostsByAuthorIds = async (authorId_list) => {
  try {
    const response = await axios.get(`${baseUrl}/authors/${authorId_list}`);
    console.log("fetchPostsByAuthorIds: ", response);
    return response.data;
  } catch (error) {
    console.error("Error getting posts:", error.message);
    throw error;
  }
}
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

export const searchPosts = async (query) => {
  try {
    const response = await axios.get(`${baseUrl}/search?query=${encodeURIComponent(query)}`);
    return response.data;
  } catch (error) {
    console.error("Error during search:", error);
    return [];
  }
};
