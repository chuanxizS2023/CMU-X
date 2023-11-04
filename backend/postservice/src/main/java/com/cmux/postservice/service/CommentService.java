package com.cmux.postservice.service;

import org.springframework.stereotype.Service;
import com.cmux.postservice.model.Comment;
import com.cmux.postservice.model.CommunityPost;
import com.cmux.postservice.dto.CommentDTO;
import java.util.NoSuchElementException;
import com.cmux.postservice.converter.CommentConverter;
import com.cmux.postservice.repository.CommentRepository;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import java.util.Optional;

@Service
public class CommentService {
    
    @Autowired
    private CommentRepository commentRepository;

    // @Autowired
    // CommunityPostService communityPostService = new CommunityPostService();
    @Autowired
    private CommentConverter commentConverter;

    public CommentDTO saveComment(CommentDTO commentdto){
        Comment comment = commentConverter.convertDTOToEntity(commentdto);
        
        commentRepository.save(comment);

        return commentConverter.convertEntityToDTO(comment);
    }

    @Transactional
    public Optional<CommentDTO> getCommentById(long commentid){
        Optional<Comment> comment = commentRepository.findById(commentid);
        if (comment.isPresent()) {
            CommentDTO commentdto = commentConverter.convertEntityToDTO(comment.get());
            return Optional.of(commentdto);
        }else {
            throw new NoSuchElementException("Comment not found for id: " + commentid);
        }
    }


}
