package com.cmux.postservice.service;

import java.util.NoSuchElementException;
import com.cmux.postservice.dto.CommunityPostDTO;
import com.cmux.postservice.model.CommunityPost;
import com.cmux.postservice.model.PostEvents;
import com.cmux.postservice.repository.CommunityPostRepository;
import com.cmux.postservice.converter.CommunityPostConverter;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

@Service
public class CommunityPostService {
    
    private CommunityPostRepository communityPostRepository;
    private CommunityPostConverter communityPostConverter;
    private final ApplicationEventPublisher publisher;

    @Autowired
    public CommunityPostService(CommunityPostRepository communityPostRepository, 
                                CommunityPostConverter communityPostConverter,
                                ApplicationEventPublisher publisher) {
        this.communityPostRepository = communityPostRepository;
        this.communityPostConverter = communityPostConverter;
        this.publisher = publisher;
    }

    public CommunityPostDTO savePost(CommunityPostDTO communityPostDTO) {

        CommunityPost communityPost = communityPostConverter.convertToEntity(communityPostDTO);

        communityPost = communityPostRepository.save(communityPost);

        // after save to mysql, publish event for elastic search

        publisher.publishEvent(new PostEvents.Created(communityPost));

        return communityPostConverter.convertToDTO(communityPost);
    }

    @Transactional
    public Optional<CommunityPostDTO> getPostById(long id) {

        Optional<CommunityPost> post = communityPostRepository.findById(id);

        if (post.isPresent()) {

            CommunityPostDTO communityPostDTO = communityPostConverter.convertToDTO(post.get());
            
            return Optional.of(communityPostDTO);

        } else {

            throw new NoSuchElementException("Post not found for id: " + id);

        }
    }
}
