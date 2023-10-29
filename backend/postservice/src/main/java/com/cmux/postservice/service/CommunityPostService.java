package com.cmux.postservice.service;

import com.cmux.postservice.model.CommunityPost;
import com.cmux.postservice.repository.CommunityPostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CommunityPostService {
    
    @Autowired
    private CommunityPostRepository communityPostRepository;

    public CommunityPost savePost(CommunityPost communityPost) {
        return communityPostRepository.save(communityPost);
    }

    public Optional<CommunityPost> getPostById(long id) {
        return communityPostRepository.findById(id);
    }

}
