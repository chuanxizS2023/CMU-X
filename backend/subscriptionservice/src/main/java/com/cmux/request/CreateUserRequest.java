package com.cmux.request;

public class CreateUserRequest {

    private String username;
    private Long userId;
    
    public CreateUserRequest() {
        
    }

    public CreateUserRequest(String username, Long userId) {
        this.username = username;
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public Long getUserId() {
        return userId;
    }
}
