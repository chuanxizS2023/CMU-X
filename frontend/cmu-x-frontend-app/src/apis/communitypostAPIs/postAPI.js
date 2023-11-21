// src/api.js
const baseUrl = 'http://localhost:9000/community';

const headers = {
  'Content-Type': 'application/json',
};

// Create a new post
export const createPost = async (postData) => {
  try {
    const response = await fetch(`${baseUrl}`, {
      method: 'POST',
      headers,
      body: JSON.stringify(postData),
    });

    if (!response.ok) {
      const errorData = await response.json();
      throw new Error(`Error ${response.status}: ${errorData.message}`);
    }

    return response;
  } catch (error) {
    console.error("Error creating post:", error.message);
    throw error;
  }
};

// Add a like to a post
export const addLike = async (postId) => {
  try {
    console.log("in addLike")
    const response = await fetch(`${baseUrl}/likes/${postId}`, {
      method: 'POST',
      headers,
    });

    if (!response.ok) {
      const errorData = await response.json();
      throw new Error(`Error ${response.status}: ${errorData.message}`);
    }

    return response;
  } catch (error) {
    console.error("Error adding like:", error.message);
    throw error;
  }
};

// Get a post by ID
export const getPostById = async (postId) => {
  try {
    const response = await fetch(`${baseUrl}/${postId}`, {
      method: 'GET',
      headers,
    });

    if (!response.ok) {
      const errorData = await response.json();
      throw new Error(`Error ${response.status}: ${errorData.message}`);
    }

    return await response.json();
  } catch (error) {
    console.error("Error getting post:", error.message);
    throw error;
  }
};

// Delete a post by ID
export const deletePost = async (postId) => {
  try {
    const response = await fetch(`${baseUrl}/${postId}`, {
      method: 'DELETE',
      headers,
    });

    if (!response.ok) {
      const errorData = await response.json();
      throw new Error(`Error ${response.status}: ${errorData.message}`);
    }

    return await response.json();
  } catch (error) {
    console.error("Error deleting post:", error.message);
    throw error;
  }
};
// Update a post by ID
export const updatePost = async (postId, updateData) => {
  try {
    const response = await fetch(`${baseUrl}/${postId}`, {
      method: 'PUT',
      headers,
      body: JSON.stringify(updateData),
    });

    if (!response.ok) {
      const errorData = await response.json();
      throw new Error(`Error ${response.status}: ${errorData.message}`);
    }

    return await response.json();
  } catch (error) {
    console.error("Error updating post:", error.message);
    throw error;
  }
};
// Mark a post as looking for teammates
export const markAsFindTeammatePost = async (postId, updateData) => {
  try {
    const response = await fetch(`${baseUrl}/find-teammate/${postId}`, {
      method: 'POST',
      headers,
      body: JSON.stringify(updateData),
    });

    if (!response.ok) {
      const errorData = await response.json();
      throw new Error(`Error ${response.status}: ${errorData.message}`);
    }

    return await response.json();
  } catch (error) {
    console.error("Error marking post for teammates:", error.message);
    throw error;
  }
};


// Add a team member to a post
export const addTeamMembers = async (postId, username) => {
  try {
    const response = await fetch(`${baseUrl}/${postId}/team-members`, {
      method: 'PUT',
      headers,
      body: JSON.stringify({ username }),
    });

    if (!response.ok) {
      const errorData = await response.json();
      throw new Error(`Error ${response.status}: ${errorData.message}`);
    }

    return await response.json();
  } catch (error) {
    console.error("Error adding team member:", error.message);
    throw error;
  }
};
