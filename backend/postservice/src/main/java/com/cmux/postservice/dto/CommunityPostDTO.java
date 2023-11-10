package com.cmux.postservice.dto;

import java.util.Date;
import java.util.List;
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

    // For find teammate post
    private boolean isFindTeammatePost;
    private String instructorName;
    private String semester;
    private String courseNumber;
    private String teamMembers;


    
}
