import { useFetchWithTokenRefresh } from "../../utils/ApiUtilsDynamic";

const baseUrl = process.env.REACT_APP_POST_URL + "community" || "";

export const usePostApi = () => {
  const fetchWithTokenRefresh = useFetchWithTokenRefresh();

  const createPost = async (postData) => {
    try {
      const response = await fetchWithTokenRefresh(`${baseUrl}`, {
        method: 'POST',
        body: JSON.stringify(postData),
        headers: {
          'Content-Type': 'application/json'
        }
      });
      return response;
    } catch (error) {
      console.error("Error creating post:", error);
    }
  };

  const addLike = async (postId) => {
    try {
      const response = await fetchWithTokenRefresh(`${baseUrl}/likes/${postId}`, {
        method: 'POST'
      });
      return response;
    } catch (error) {
      console.error("Error adding like:", error);
    }
  };

  const getPostById = async (postId) => {
    try {
      const response = await fetchWithTokenRefresh(`${baseUrl}/${postId}`, {
        method: 'GET'
      });
      return response.data;
    } catch (error) {
      console.error("Error getting post:", error);
    }
  };

  const fetchPostsByAuthorIds = async (authorId_list) => {
    try {
      console.log("fetchPostsByAuthorIds: ", authorId_list)
      const response = await fetchWithTokenRefresh(`${baseUrl}/authors/${authorId_list}`, {
        method: 'GET'
      });
      if (response.ok) {
        const data = await response.json(); // Parse the JSON data from the response
        return data;
      } else {
        throw new Error(`HTTP error! status: ${response.status}`);
      }
    } catch (error) {
      console.error("Error getting posts:", error.message);
      throw error;
    }
  };

  const deletePost = async (postId) => {
    try {
      const response = await fetchWithTokenRefresh(`${baseUrl}/${postId}`, {
        method: 'DELETE'
      });
      return response;
    } catch (error) {
      console.error("Error deleting post:", error);
    }
  };

  const updatePost = async (postId, updateData) => {
    try {
      const response = await fetchWithTokenRefresh(`${baseUrl}/${postId}`, {
        method: 'PUT',
        body: JSON.stringify(updateData),
        headers: {
          'Content-Type': 'application/json'
        }
      });
      return response;
    } catch (error) {
      console.error("Error updating post:", error);
    }
  };

  const markAsFindTeammatePost = async (postId, updateData) => {
    try {
      const response = await fetchWithTokenRefresh(`${baseUrl}/find-teammate/${postId}`, {
        method: 'POST',
        body: JSON.stringify(updateData),
        headers: {
          'Content-Type': 'application/json'
        }
      });
      return response;
    } catch (error) {
      console.error("Error marking post for teammates:", error);
    }
  };

  const addTeamMembers = async (postId, username) => {
    try {
      const response = await fetchWithTokenRefresh(`${baseUrl}/${postId}/team-members`, {
        method: 'PUT',
        body: JSON.stringify({ username }),
        headers: {
          'Content-Type': 'application/json'
        }
      });
      return response;
    } catch (error) {
      console.error("Error adding team member:", error);
    }
  };

  const searchPosts = async (query) => {
    try {
      const response = await fetchWithTokenRefresh(`${baseUrl}/search?query=${encodeURIComponent(query)}`, {
        method: 'GET'
      });
      return response.data;
    } catch (error) {
      console.error("Error during search:", error);
    }
  };

  return {
    createPost,
    addLike,
    getPostById,
    fetchPostsByAuthorIds,
    deletePost,
    updatePost,
    markAsFindTeammatePost,
    addTeamMembers,
    searchPosts
  };
};

