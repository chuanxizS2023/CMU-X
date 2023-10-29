package com.cmux.postservice.controller;

import com.cmux.postservice.model.CommunityPost;
import com.cmux.postservice.service.CommunityPostService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/community")
public class CommunityPostController {
    
    @Autowired
    private CommunityPostService communityPostService;

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

    @GetMapping("/{id}")
    public Optional<CommunityPost> getPostById(@PathVariable long communityPostid){
        return communityPostService.getPostById(communityPostid);
    }
}
