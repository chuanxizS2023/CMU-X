// src/api.js
const baseUrl = 'http://localhost:9000/community';

const headers = {
  'Content-Type': 'application/json',
};

// Create a new post
export const createPost = async (postData) => {
  const response = await fetch(`${baseUrl}`, {
    method: 'POST',
    headers,
    body: JSON.stringify(postData),
  });
  return response.json();
};

// Add a like to a post
export const addLike = async (postId) => {
  const response = await fetch(`${baseUrl}/likes/${postId}`, {
    method: 'POST',
    headers,
  });
  return response.json();
};

// Get a post by ID
export const getPostById = async (postId) => {
    console.log("fetching from: " + `${baseUrl}/${postId}`)
  const response = await fetch(`${baseUrl}/${postId}`, {
    method: 'GET',
    headers,
  });
  console.log("response: " + response)
  return response.json();
};

// Delete a post by ID
export const deletePost = async (postId) => {
  const response = await fetch(`${baseUrl}/${postId}`, {
    method: 'DELETE',
    headers,
  });
  return response.json();
};

// Update a post by ID
export const updatePost = async (postId, updateData) => {
  const response = await fetch(`${baseUrl}/${postId}`, {
    method: 'PUT',
    headers,
    body: JSON.stringify(updateData),
  });
  return response.json();
};

// Mark a post as looking for teammates
export const markAsFindTeammatePost = async (postId, updateData) => {
  const response = await fetch(`${baseUrl}/find-teammate/${postId}`, {
    method: 'POST',
    headers,
    body: JSON.stringify(updateData),
  });
  return response.json();
};

// Add a team member to a post
export const addTeamMembers = async (postId, username) => {
  const response = await fetch(`${baseUrl}/${postId}/team-members`, {
    method: 'PUT',
    headers,
    body: JSON.stringify({ username }),
  });
  return response.json();
};
