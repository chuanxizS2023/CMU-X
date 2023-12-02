package com.cmux.postservice.listeners;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.messaging.simp.SimpMessagingTemplate;


import com.cmux.postservice.service.CommentService;
import com.cmux.postservice.service.CommunityPostService;
import com.cmux.postservice.model.PostEvents;

@Component
public class PostEventListener {

    @Autowired
    private CommunityPostService communityPostService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    private final String id = "communitypost";

    @EventListener
    public void onPostCreated(PostEvents.Created event) {
        String postID = String.valueOf(event.getCommunityPost().getCommunityPostid());

        communityPostService.index(this.id, postID, event.getCommunityPost());

        messagingTemplate.convertAndSend("/topic/post-created", event.getCommunityPost())
        System.out.println("PostEventListener: onPostCreated: indexed document with id " + postID);
    }


    @EventListener
    public void onPostUpdated(PostEvents.Updated event) {
        String postID = String.valueOf(event.getCommunityPost().getCommunityPostid());


        communityPostService.index(this.id, String.valueOf(event.getCommunityPost().getCommunityPostid()),
                event.getCommunityPost());

        messagingTemplate.convertAndSend("/topic/post-update", event.getCommunityPost());
    }

    @EventListener
    public void onPostDeleted(PostEvents.Deleted event) {
        String postID = String.valueOf(event.getPostId());

        commentService.deleteIndex(this.id, postID);

        System.out.println("PostEventListener: onPostDeleted: deleted document with id " + postID);
    
        messagingTemplate.convertAndSend("/topic/post-delete", event.getPostId());
    }
}
