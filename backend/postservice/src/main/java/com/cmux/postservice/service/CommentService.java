package com.cmux.postservice.service;

import org.springframework.stereotype.Service;
import com.cmux.postservice.model.Comment;
import com.cmux.postservice.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class CommentService {
    
    @Autowired
    private CommentRepository commentRepository;

    public Comment saveComment(Comment comment){
        return commentRepository.save(comment);
    }

    public Comment getCommentById(long commentid){
        return commentRepository.findById(commentid).orElse(null);
    }
}
