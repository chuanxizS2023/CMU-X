package com.cmux.postservice.listeners;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import com.cmux.postservice.service.CommunityPostService;
// import com.cmux.postservice.service.ElasticsearchService;
import com.cmux.postservice.model.PostEvents;

@Component
public class PostEventListener {
    
    @Autowired
    private CommunityPostService communityPostService;

    private final String id = "communitypost";
    // public PostEventListener(ElasticsearchService elasticsearchService) {
    //     this.elasticsearchService = elasticsearchService;
    // }

    @EventListener
    public void onPostCreated(PostEvents.Created event) {
        communityPostService.index(this.id,String.valueOf(event.getCommunityPost().getCommunityPostid()), event.getCommunityPost());
    }

    // @EventListener
    // public void onPostUpdated(PostEvents.Updated event) {
    //     elasticsearchService.updatePost(event.getCommunityPost());
    // }

}
