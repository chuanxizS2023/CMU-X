import axios from 'axios';

const API_URL = 'http://localhost:9000/comments'; 


export const saveComment = async (commentData) => {
    try {
        const response = await axios.post(`${API_URL}`, commentData);
        return response.data;
    } catch (error) {
        console.error('Error saving comment', error);
        throw error;
    }
};

export const getComment = async (commentId) => {
    try {
        const response = await axios.get(`${API_URL}/${commentId}`);
        return response;
    } catch (error) {
        console.error('Error fetching comment', error);
        throw error;
    }
};

export const deleteComment = async (commentId) => {
    try {
        const response = await axios.delete(`${API_URL}/${commentId}`);
        return response;
    } catch (error) {
        console.error('Error deleting comment', error);
        throw error;
    }
};

export const updateComment = async (commentId, commentData) => {
    try {
        const response = await axios.put(`${API_URL}/${commentId}`, commentData);
        return response;
    } catch (error) {
        console.error('Error updating comment', error);
        throw error;
    }
};
