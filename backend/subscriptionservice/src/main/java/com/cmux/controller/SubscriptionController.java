package com.cmux.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.cmux.entity.User;
import com.cmux.request.GetSubscribersRequest;
import com.cmux.request.CreateUserRequest;
import com.cmux.service.SubscriptionService;

import java.util.List;

@RestController
@RequestMapping("")
public class SubscriptionController {

    @Autowired
    private SubscriptionService subscriptionService;


    // Create a new user with name and userId
    @PostMapping("/create-user")
    public User createUser(@RequestBody CreateUserRequest createUserRequest) {
        System.out.println("Create user with userId " + createUserRequest.getUserId());
        return subscriptionService.createUser(createUserRequest.getUserId(), createUserRequest.getUsername());
    }


    // Get all subscribers for a specific user
    @GetMapping("/subscribers")
    public List<User> getAllSubscribers(@RequestBody GetSubscribersRequest req) {
        System.out.println("Get all subscribers for user " + req.getUserId());
        return subscriptionService.getAllSubscribers(req.getUserId());
    }

    // Get all subscriptions for a specific user
    @GetMapping("subscriptions")
    public List<User> getAllSubscriptions(@RequestBody Long userId) {
        return subscriptionService.getAllSubscriptions(userId);
    }

    // Get all mutual subscriptions for a specific user
    @GetMapping("/mutual-subscribe")
    public List<User> getAllMutualSubscriptions(@RequestBody Long userId) {
        return subscriptionService.getAllMutualSubscriptions(userId);
    }

    // Add a subscription for a user
    @PostMapping("/subscribe")
    public void addSubscription(@RequestParam Long userId, @RequestBody Long subscriptionId) {
        subscriptionService.addSubscription(userId, subscriptionId);
    }

    // Remove a subscription from a user
    @DeleteMapping("/unsubscribe")
    public void removeSubscription(@RequestParam Long userId, @RequestBody Long subscriptionId) {
        subscriptionService.removeSubscription(userId, subscriptionId);
    }
}
