package com.cmux.postservice.controller;

import com.cmux.postservice.dto.CommunityPostDTO;
import com.cmux.postservice.service.CommunityPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/community")
public class CommunityPostController {
    
    @Autowired
    private CommunityPostService communityPostService;

    @PostMapping
    public CommunityPostDTO createPost(@RequestBody CommunityPostDTO postDTO) {
        // Here you should throw AccessDeniedException if access is denied
        // For example: if(!hasAccess()) throw new AccessDeniedException("Access Denied");
        return communityPostService.savePost(postDTO);
    }

    @GetMapping("/{communityPostid}")
    public CommunityPostDTO getPostById(@PathVariable long communityPostid) {
        return communityPostService.getPostById(communityPostid)
                .orElseThrow(() -> new NoSuchElementException("Post not found with id: " + communityPostid));
    }

    @GetMapping("/posts/{id}")
    public CommunityPostDTO getPost(@PathVariable Long id) {
        return communityPostService.getPostById(id)
                .orElseThrow(() -> new NoSuchElementException("Post not found with id: " + id));
    }
}