package com.cmux.postservice.controller;

import com.cmux.postservice.dto.CommunityPostDTO;
import com.cmux.postservice.service.CommunityPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;
import java.util.NoSuchElementException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("/community")
public class CommunityPostController {
    
    @Autowired
    private CommunityPostService communityPostService;

    @PostMapping
    public CommunityPostDTO createPost(@RequestBody CommunityPostDTO postDTO) {
        
        return communityPostService.savePost(postDTO);
    }

    @PostMapping("/{communityPostId}/likes")
    public ResponseEntity<?> addLike(@PathVariable long communityPostId) {
        try {
            communityPostService.addLikeToPost(communityPostId);
            return new ResponseEntity<>("Like added successfully", HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Error adding like", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{communityPostid}")
    public CommunityPostDTO getPostById(@PathVariable long communityPostid) {
        return communityPostService.getPostById(communityPostid)
                .orElseThrow(() -> new NoSuchElementException("Post not found with id: " + communityPostid));
    }

    @DeleteMapping("/{communityPostId}")
    public ResponseEntity<?> deletePost(@PathVariable long communityPostId) {
        try {
            communityPostService.deletePostById(communityPostId);
            return new ResponseEntity<>("Post deleted successfully", HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("Error deleting post", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{communityPostId}")
    public ResponseEntity<?> updatePost(@PathVariable long communityPostId, @RequestBody CommunityPostDTO postDTO) {
        try {
            CommunityPostDTO updatedPost = communityPostService.updatePost(communityPostId, postDTO);
            System.out.println("updatedPost controller");
            return new ResponseEntity<>(updatedPost, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
        // } catch (AccessDeniedException e) {
        //     return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
        // } 
        catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Error updating post" + e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}