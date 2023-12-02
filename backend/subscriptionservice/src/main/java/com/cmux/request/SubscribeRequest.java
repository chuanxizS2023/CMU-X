package com.cmux.request;

public class SubscribeRequest {

    // Define the properties of the subscription, such as user ID and subscription details
    private String userId;
    private String subscriptionType;
    // ... other relevant fields

    public SubscribeRequest(String userId, String subscriptionType /*, other fields */) {
        this.userId = userId;
        this.subscriptionType = subscriptionType;
    }

    // Getters and setters for each field
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getSubscriptionType() {
        return subscriptionType;
    }

    public void setSubscriptionType(String subscriptionType) {
        this.subscriptionType = subscriptionType;
    }
}
