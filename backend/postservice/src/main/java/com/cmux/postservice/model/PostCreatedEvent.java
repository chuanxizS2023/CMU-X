package com.cmux.postservice.model;

import lombok.Data;

@Data
public class PostCreatedEvent {
    private final CommunityPost communityPost;

    public PostCreatedEvent(CommunityPost communityPost) {
        System.out.println("PostCreatedEvent: constructor");
        this.communityPost = communityPost;
    }

}
