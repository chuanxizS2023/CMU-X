package com.cmux.postservice.converter;

import java.util.ArrayList;


import com.cmux.postservice.dto.CommentDTO;
import org.springframework.stereotype.Component;
import com.cmux.postservice.dto.CommunityPostDTO;
import com.cmux.postservice.model.Comment;
import com.cmux.postservice.model.CommunityPost;

@Component
public class CommunityPostConverter {

    public CommunityPostDTO convertToDTO(CommunityPost communityPost){
        CommentConverter commentConverter = new CommentConverter();
        CommunityPostDTO dto = new CommunityPostDTO();
        dto.setCommunityPostid(communityPost.getCommunityPostid());
        dto.setTitle(communityPost.getTitle());
        dto.setContent(communityPost.getContent());
        dto.setCreated_Date(communityPost.getCreated_Date());
        dto.setAuthor_id(communityPost.getAuthor_id());
        dto.setLikes(communityPost.getLikes());
        dto.setCommentsCount(communityPost.getCommentsCount());
        dto.set_published(communityPost.is_published());
        if(communityPost.getComments() != null) {
            ArrayList<CommentDTO> commentList = new ArrayList<CommentDTO>();
            for (int i = 0; i < communityPost.getComments().size(); i++) {
                commentList.add(commentConverter.convertEntityToDTO(communityPost.getComments().get(i)));
            }
            dto.setComments(commentList);


            // dto.setComments(communityPost.getComments().stream()
            //     .map(commentService.convertEntityToDTO)
            //     .collect(Collectors.toList()));
        }else{
            dto.setComments(null);
        }
        return dto;
    }


    public CommunityPost convertToEntity(CommunityPostDTO communityPostDTO) {
        CommunityPost communityPost = new CommunityPost();
        CommentConverter commentConverter = new CommentConverter();
        communityPost.setTitle(communityPostDTO.getTitle());
        communityPost.setContent(communityPostDTO.getContent());
        communityPost.setCreated_Date(communityPostDTO.getCreated_Date());
        communityPost.setAuthor_id(communityPostDTO.getAuthor_id());
        communityPost.setLikes(communityPostDTO.getLikes());
        communityPost.setCommentsCount(communityPostDTO.getCommentsCount());
        communityPost.set_published(communityPostDTO.is_published());
        // set comments
        if(communityPostDTO.getComments() != null) {
            ArrayList<Comment> commentList = new ArrayList<Comment>();
            for (int i = 0; i < communityPostDTO.getComments().size(); i++) {
                commentList.add(commentConverter.convertDTOToEntity(communityPostDTO.getComments().get(i)));
            }
            communityPost.setComments(commentList);
        }else{
            communityPost.setComments(null);
        }

        return communityPost;
    }
}
