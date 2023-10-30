package com.cmux.postservice.service;

import com.cmux.postservice.model.Comment;
import com.cmux.postservice.dto.CommentDTO;
import com.cmux.postservice.dto.CommunityPostDTO;
import com.cmux.postservice.model.CommunityPost;
import com.cmux.postservice.repository.CommunityPostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CommunityPostService {
    
    @Autowired
    private CommunityPostRepository communityPostRepository;

    public CommunityPostDTO savePost(CommunityPostDTO communityPostDTO) {
        CommunityPost communityPost = convertToEntity(communityPostDTO);

        communityPost = communityPostRepository.save(communityPost);
        return convertToDTO(communityPost);
    }

    @Transactional
    public Optional<CommunityPostDTO> getPostById(long id) {
        Optional<CommunityPost> post = communityPostRepository.findById(id);
        return post.map(this::convertToDTO);
    }
    

    // why put convert to dto here? (might later move to DTO/entity layer)
    //     All business logic, including entity-to-DTO conversions, is centralized in the service layer. This ensures that controllers remain lean and focused solely on handling requests and responses.
    // Consistency: If there are other operations or logic needed during the conversion (e.g., fetching related entities, handling exceptions), having it in the service layer ensures consistent behavior.
    // Encapsulation: The service layer can hide the details of the conversion, providing a cleaner interface to the controller.
    private CommunityPostDTO convertToDTO(CommunityPost communityPost){
        CommunityPostDTO dto = new CommunityPostDTO();
        dto.setCommunityPostid(communityPost.getCommunityPostid());
        dto.setTitle(communityPost.getTitle());
        dto.setContent(communityPost.getContent());
        dto.setCreated_Date(communityPost.getCreated_Date());
        dto.setAuthor_id(communityPost.getAuthor_id());
        dto.setLikes(communityPost.getLikes());
        dto.setCommentsCount(communityPost.getCommentsCount());
        if(communityPost.getComments() != null) {
            dto.setComments(communityPost.getComments().stream()
                .map(this::convertCommentToDTO)
                .collect(Collectors.toList()));
        }
        return dto;
    }


    private CommentDTO convertCommentToDTO(Comment comment) {
        CommentDTO dto = new CommentDTO();
        dto.setCommentid(comment.getCommentid());
        dto.setContent(comment.getContent());
        dto.setCreated_Date(comment.getCreated_Date());
        dto.setAuthor_id(comment.getAuthor_id());
        dto.setLikes(comment.getLikes());

        return dto;
    }

    private CommunityPost convertToEntity(CommunityPostDTO communityPostDTO) {
        CommunityPost communityPost = new CommunityPost();
        communityPost.setTitle(communityPostDTO.getTitle());
        communityPost.setContent(communityPostDTO.getContent());
        communityPost.setCreated_Date(communityPostDTO.getCreated_Date());
        communityPost.setAuthor_id(communityPostDTO.getAuthor_id());
        communityPost.setLikes(communityPostDTO.getLikes());
        communityPost.setCommentsCount(communityPostDTO.getCommentsCount());
        // Do not set comments here to avoid cyclic dependency
        return communityPost;
    }
    
}
