package com.cmux.postservice.model;

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
    private Date created;
}
