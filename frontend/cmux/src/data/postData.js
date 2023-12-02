import { getPostById } from '../apis/communitypostAPIs/postAPI';

export const fetchPostsByIds = async (postIds) => {
  try {
    const postsPromises = postIds.map(id => getPostById(id));
    const posts = await Promise.all(postsPromises);
    return posts;
  } catch (error) {
    console.error('Error fetching posts:', error);
    throw error;
  }
};
