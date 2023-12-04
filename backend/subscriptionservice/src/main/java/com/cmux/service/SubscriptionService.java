package com.cmux.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import com.cmux.repository.UserRepository;

import jakarta.validation.ConstraintViolationException;

import com.cmux.entity.User;

import java.util.List;

@Service
public class SubscriptionService {

	// init SubscriptionService
	SubscriptionService() {

	}

	@Autowired
	UserRepository userRepository;

	// Create a new user with name and userId
	public User createUser(Long userId, String name) {
		return userRepository.createUser(userId, name);
	}

	// Get all subscribers for a specific user
	public List<User> getAllFollowers(Long userId) {
		return userRepository.findFollowersByUserId(userId);
	}

	// Get all subscriptions for a specific user
	public List<User> getAllSubscriptions(Long userId) {
		return userRepository.findSubscriptionsByUserId(userId);
	}

	// Get all mutual subscriptions for a specific user
	public List<User> getAllMutualSubscriptions(Long userId) {
		return userRepository.findMutualSubscriptionsByUserId(userId);
	}

	// Add a subscription for a user
	public void addSubscription(Long userId, Long subscriptionId) {
		try {
			// Assuming userRepository.addSubscription checks if both users exist
			userRepository.addSubscription(userId, subscriptionId);
		} catch (DataAccessException e) {
			System.out.println("DataAccessException");
		} catch (ConstraintViolationException e) {
			System.out.println("ConstraintViolationException");
		}
	}

	// Remove a subscription from a user
	public void removeSubscription(Long userId, Long subscriptionId) {
		userRepository.removeSubscription(userId, subscriptionId);
	}


}
