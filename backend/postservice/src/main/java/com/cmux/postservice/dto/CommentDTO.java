package com.cmux.postservice.dto;

import lombok.Getter;
import lombok.Setter;
import com.cmux.postservice.model.Comment;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.cmux.postservice.model.CommunityPost;
import com.cmux.postservice.repository.CommunityPostRepository;
import com.cmux.postservice.service.CommunityPostService;
import com.cmux.postservice.dto.CommunityPostDTO;
import java.util.Date;

@Getter
@Setter
public class CommentDTO {
    
    private long commentid;
    private String content;
    private Date created_Date;
    private String author_id;
    private long likes;
    private long communityPostid;

    
}
