package com.cmux.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.cmux.entity.User;
import com.cmux.request.GetSubscribersRequest;
import com.cmux.request.GetSubscriptionsRequest;
import com.cmux.request.CreateUserRequest;
import com.cmux.request.UnsubscribeRequest;
import com.cmux.request.SubscribeRequest;
import com.cmux.service.SubscriptionService;

import java.util.List;

@RestController
@RequestMapping("")
public class SubscriptionController {

    @Autowired
    private SubscriptionService subscriptionService;

    // Get all subscribers for a specific user
    @GetMapping("/followers")
    public List<User> getAllFollowers(@RequestBody GetSubscribersRequest req) {
        System.out.println("Get all followers for user " + req.getUserId());
        return subscriptionService.getAllFollowers(req.getUserId());
    }

    @GetMapping("/followers/mutual")
    public List<User> getAllMutualFollowers(@RequestBody GetSubscribersRequest req) {
        System.out.println("Get all mutual followers for user " + req.getUserId());
        return subscriptionService.getAllMutualSubscriptions(req.getUserId());
    }

    // Get numbers of subscribers for a specific user
    @GetMapping("/followers/count")
    public int getFollowersCount(@RequestBody GetSubscribersRequest req) {
        return subscriptionService.getAllFollowers(req.getUserId()).size();
    }

    // Get all subscriptions for a specific user
    @GetMapping("/subscriptions")
    public List<User> getAllSubscriptions(@RequestBody GetSubscriptionsRequest req) {
        System.out.println("Get all subscriptions for user " + req.getUserId());
        return subscriptionService.getAllSubscriptions(req.getUserId());
    }

    // Get numbers of subscriptions for a specific user
    @GetMapping("/subscriptions/count")
    public int getSubscriptionsCount(@RequestBody GetSubscriptionsRequest req) {
        return subscriptionService.getAllSubscriptions(req.getUserId()).size();
    }

    // Add a subscription for a user
    @PutMapping("/subscriptions")
    public void addSubscription(@RequestBody SubscribeRequest req) {
        System.out.println("Add subscription for user " + req.getUserId());
        System.out.println("Add subscription to user " + req.getUserIdSubscribeTo());
        subscriptionService.addSubscription(req.getUserId(), req.getUserIdSubscribeTo());
    }

    // Create a new user with name and userId
    @PostMapping("/subscriptions")
    public User createUser(@RequestBody CreateUserRequest createUserRequest) {
        System.out.println("Create user with userId " + createUserRequest.getUserId());
        return subscriptionService.createUser(createUserRequest.getUserId(), createUserRequest.getUsername());
    }

    // Remove a subscription from a user
    @DeleteMapping("/subscriptions")
    public void removeSubscription(@RequestBody UnsubscribeRequest req) {
        subscriptionService.removeSubscription(req.getUserId(), req.getUserIdAnother());
    }
}
