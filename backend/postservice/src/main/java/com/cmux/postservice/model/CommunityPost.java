package com.cmux.postservice.model;

import lombok.Data;
import jakarta.persistence.*;

@Data
@Entity
@Table(name = "communitypost")
public class CommunityPost {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long communityPostid;

    @ManyToOne
    @JoinColumn(name = "userid")
    private User user;
}
