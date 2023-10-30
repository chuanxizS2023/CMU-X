package com.cmux.postservice.controller;

import com.cmux.postservice.model.Comment;
import com.cmux.postservice.service.CommentService;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/community/comments")

public class CommentController {
    
    @Autowired
    private CommentService commentService;

    @PostMapping
    public Comment savComment(@RequestBody Comment comment){
        return commentService.saveComment(comment);
    }

    @GetMapping("/{commentid}")
    public Comment getComment(@PathVariable long commentid){
        return commentService.getCommentById(commentid);
    }
}
