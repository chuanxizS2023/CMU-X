package com.cmux.postservice.model;

import lombok.Data;
import java.util.Date;
import jakarta.persistence.*;


@Data
@Entity
@Table(name = "comment")
public class Comment {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long commentid;

    private String content;
    private Date created_Date;
    private String author_id;
    private long likes;
    
    @ManyToOne
    @JoinColumn(name = "communityPostid")
    private CommunityPost communityPost;
}
