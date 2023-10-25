package com.cmux.postservice.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
public class CommunityPostController {
    

    @GetMapping("/communitypost")
    public String getCommunityPost() {
        System.out.println("Here display the community2312312 ");
        return "Here display the community ";
    }

    @GetMapping("/c")
    public String getCommunityPost2() {
        return "Here display newsssst";
    }
}
