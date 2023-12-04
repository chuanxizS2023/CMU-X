package com.cmux.postservice.model;

import lombok.Data;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.*;


@Data
@Entity
@Table(name = "comment")
public class Comment {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long commentid;

    private String content;
    private String created_Date;
    private String author_id;
    private String username;
    private long likes;
    
    @ManyToOne(fetch = FetchType.LAZY) // Changed to LAZY to prevent immediate fetching
    @JoinColumn(name = "communityPostid")
    @JsonBackReference // This annotation will prevent the serialization of the referenced CommunityPost to avoid recursion.
    private CommunityPost communityPost;
}
