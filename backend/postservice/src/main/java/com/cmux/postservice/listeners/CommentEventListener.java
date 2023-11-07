package com.cmux.postservice.listeners;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import com.cmux.postservice.model.CommentEvents;
import com.cmux.postservice.service.CommentService;

@Component
public class CommentEventListener {
    
    @Autowired
    private CommentService commentService;
    private final String index = "comment";
    // public PostEventListener(ElasticsearchService elasticsearchService) {
    //     this.elasticsearchService = elasticsearchService;
    // }

    @EventListener
    public void onCommentCreated(CommentEvents.Created event) {
        String id = String.valueOf(event.getComment().getCommentid());

        commentService.index(this.index, id, event.getComment());

        System.out.println("Comment indexed");
    }

    @EventListener 
    public void onCommentDeleted(CommentEvents.Deleted event){
        String id = String.valueOf(event.getCommentId());

        commentService.deleteIndex(this.index, id);

        System.out.println("Comment deleted");
    }

    @EventListener
    public void onCommentUpdated(CommentEvents.Updated event){
        String id = String.valueOf(event.getComment().getCommentid());

        commentService.index(this.index, id, event.getComment());

        System.out.println("Comment updated");
    }
    // @EventListener
    // public void onCommentUpdated(CommentEvents.Updated event) {
    //     elasticsearchService.updateComment(event.getComment());
    // }
}
