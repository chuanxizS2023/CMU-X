package com.cmux.postservice.listeners;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import com.cmux.postservice.service.ElasticsearchService;
import com.cmux.postservice.model.PostCreatedEvent;

@Component
public class PostEventListener {
    
    @Autowired
    private ElasticsearchService elasticsearchService;

    // public PostEventListener(ElasticsearchService elasticsearchService) {
    //     this.elasticsearchService = elasticsearchService;
    // }

    @EventListener
    public void onPostCreated(PostCreatedEvent event) {
        elasticsearchService.indexPost(event.getCommunityPost());
    }

}
