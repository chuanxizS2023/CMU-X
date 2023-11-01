package com.cmux.postservice.controller;

import com.cmux.postservice.dto.CommentDTO;
import com.cmux.postservice.service.CommentService;

import java.util.NoSuchElementException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/community/comments")

public class CommentController {
    
    @Autowired
    private CommentService commentService;

    @PostMapping
    public CommentDTO savComment(@RequestBody CommentDTO commentdto){
        return commentService.saveComment(commentdto);
    }

    @GetMapping("/{commentid}")
    public CommentDTO getComment(@PathVariable long commentid) {
        return commentService.getCommentById(commentid)
                .orElseThrow(() -> new NoSuchElementException("comment not found with id: " + commentid));
    }
}
