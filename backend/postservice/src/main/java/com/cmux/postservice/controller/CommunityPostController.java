package com.cmux.postservice.controller;

import com.cmux.postservice.model.CommunityPost;
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


    @GetMapping("/communitypost")
    public String getCommunityPost() {
        System.out.println("Here display the community2312312 ");
        return "Here display the community post ";
    }

    @GetMapping("/c")
    public String getCommunityPost2() {
        return "Here display newsssst";
    }

    @PostMapping
    public CommunityPost createPost(@RequestBody CommunityPost post){
        return communityPostService.savePost(post);
    }

    @GetMapping("/{communityPostid}")
    public Optional<CommunityPost> getPostById(@PathVariable long communityPostid){
        return communityPostService.getPostById(communityPostid);
    }

    @GetMapping("/post/{id}")
    public Optional<CommunityPost> getPost(@PathVariable Long id) {
        Optional<CommunityPost> post = communityPostService.getPostById(id);
        logger.info("Retrieved post: " + post);
        System.out.println("Retrieved post: " + post);
        return post;
    }
}
