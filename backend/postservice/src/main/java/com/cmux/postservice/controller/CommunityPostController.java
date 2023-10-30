package com.cmux.postservice.controller;

import com.cmux.postservice.model.CommunityPost;
import com.cmux.postservice.dto.CommunityPostDTO;
import com.cmux.postservice.dto.CommentDTO;
import com.cmux.postservice.service.CommunityPostService;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/community")
public class CommunityPostController {
    
    @Autowired
    private CommunityPostService communityPostService;

    private static final Logger logger = LoggerFactory.getLogger(CommunityPostController.class);

    @PostMapping
    public CommunityPostDTO createPost(@RequestBody CommunityPostDTO postDTO){
        System.out.println("Creating post: " + postDTO);
        System.out.println("Creating post: " + postDTO.getTitle());
        System.out.println("Creating post: " + postDTO.getContent());
        System.out.println("Creating post: " + postDTO.getAuthor_id());
        System.out.println("Creating post: " + postDTO.getCreated_Date());
        System.out.println("Creating post: " + postDTO.getLikes());
        return communityPostService.savePost(postDTO);
    }

    
    @GetMapping("/{communityPostid}")
    public Optional<CommunityPostDTO> getPostById(@PathVariable long communityPostid){
        return communityPostService.getPostById(communityPostid);
    }

    @GetMapping("/posts/{id}")
    public Optional<CommunityPostDTO> getPost(@PathVariable Long id) {
        Optional<CommunityPostDTO> post = communityPostService.getPostById(id);
        System.out.println("Retrieved post: " + post);
        return post;
    }

}
