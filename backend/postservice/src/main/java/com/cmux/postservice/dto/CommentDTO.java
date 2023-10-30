package com.cmux.postservice.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class CommentDTO {
    
    private long commentid;
    private String content;
    private Date created_Date;
    private String author_id;
    private long likes;
}
