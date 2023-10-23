package com.cmux.postservice.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
public class CommunityPostController {
    

    @GetMapping("/communitypost")
    public String getCommunityPost() {
        return "Here display the community post";
    }
}
