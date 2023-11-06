package com.cmux.postservice.model;

import lombok.Data;

public class CommentEvents {
    
    @Data
    public static class Created {
        private final Comment comment;
        // Constructor, getters, setters
        public Created(Comment comment) {
            this.comment = comment;
        }
    }
    
    @Data
    public static class Updated {
        private final Comment comment;
        // Constructor, getters, setters
        public Updated(Comment comment) {
            this.comment = comment;
        }
    }
    
    @Data
    public static class Deleted {
        private final long commentId;
        // Constructor, getters, setters
        public Deleted(long commentId) {
            this.commentId = commentId;
        }
    }
    
}