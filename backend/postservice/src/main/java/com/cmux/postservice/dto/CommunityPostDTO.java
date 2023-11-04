package com.cmux.postservice.dto;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import com.cmux.postservice.model.Comment;
import com.cmux.postservice.dto.CommentDTO;
import com.cmux.postservice.model.CommunityPost;
import com.cmux.postservice.service.CommentService;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommunityPostDTO {

    private long communityPostid;
    private String title;
    private String content;
    private Date created_Date;
    private String author_id;
    private long likes;
    private int commentsCount;
    private boolean is_published;
    private List<CommentDTO> comments;

    


    
}
