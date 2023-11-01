package com.cmux.postservice.model;

import java.util.List;
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
    
    // @ManyToOne
    // @JoinColumn(name = "userid")
    // private User user;

    private String title;
    private String content;
    private Date created_Date;
    // foreign key from user table
    private String author_id;
    private long likes;
    private int commentsCount;
    private boolean is_published;
    @OneToMany(mappedBy = "communityPost", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Comment> comments;

}
