package com.cmux.postservice.service;

import org.springframework.stereotype.Service;
import com.cmux.postservice.model.Comment;
import com.cmux.postservice.model.CommunityPost;
import com.cmux.postservice.dto.CommentDTO;
import com.cmux.postservice.dto.CommunityPostDTO;
import com.cmux.postservice.repository.CommentRepository;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import java.util.Optional;

@Service
public class CommentService {
    
    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    CommunityPostService communityPostService = new CommunityPostService();


    public CommentDTO saveComment(CommentDTO commentdto){
        Comment comment = convertDTOToEntity(commentdto);
        
        commentRepository.save(comment);

        return convertEntityToDTO(comment);
    }

    @Transactional
    public Optional<CommentDTO> getCommentById(long commentid){
        Optional<Comment> comment = commentRepository.findById(commentid);
        
        return comment.map(this::convertEntityToDTO);
    }

    public CommentDTO convertEntityToDTO(Comment comment){
        CommentDTO dto = new CommentDTO();
        dto.setCommentid(comment.getCommentid());
        dto.setContent(comment.getContent());
        dto.setCreated_Date(comment.getCreated_Date());
        dto.setAuthor_id(comment.getAuthor_id());
        dto.setLikes(comment.getLikes());
        return dto;
    }

    public Comment convertDTOToEntity(CommentDTO commentdto){
        Comment comment = new Comment();

        comment.setCommentid(commentdto.getCommentid());
        comment.setContent(commentdto.getContent());
        comment.setCreated_Date(commentdto.getCreated_Date());
        comment.setAuthor_id(commentdto.getAuthor_id());
        comment.setLikes(commentdto.getLikes());
        Optional<CommunityPostDTO> communityPostDTOOptional = communityPostService.getPostById(commentdto.getCommunityPostid());
        if (communityPostDTOOptional.isPresent()) {
            CommunityPost communityPost = communityPostService.convertToEntity(communityPostDTOOptional.get());
            comment.setCommunityPost(communityPost);
        } else {
            // Handle the case when the community post is not found
            // For example, throw an exception or return a specific error response
            throw new RuntimeException("Community post not found");
        }

        return comment;
    }
}
