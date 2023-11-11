package com.cmux.postservice.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Data;
import java.util.Date;
import jakarta.persistence.*;

@Data
@Entity
@Table(name = "communitypost")
public class CommunityPost {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long communityPostid;
    
    private String title;
    private String content;
    private Date created_Date;
    // foreign key from user table
    private String author_id;
    private long likes;
    private int commentsCount;
    private boolean is_published;
    @OneToMany(mappedBy = "communityPost", cascade = CascadeType.ALL, fetch = FetchType.LAZY) // Changed to LAZY to prevent immediate fetching
    @JsonManagedReference // This annotation is used to overcome the recursion problem.
    private List<Comment> comments;

    // For find teammate post
    private boolean isFindTeammatePost;
    private String instructorName;
    private String courseNumber;
    private String semester;
    private List<String> teamMembers;

}
