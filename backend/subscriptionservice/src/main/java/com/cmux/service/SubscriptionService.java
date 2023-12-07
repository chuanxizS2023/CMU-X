package com.cmux.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cmux.entity.User;
import com.cmux.service.strategy.SubscriptionStrategy;
import com.cmux.repository.UserRepository;
import com.cmux.service.strategy.UserCreation;
import com.cmux.service.strategy.UserRetrievalStrategy;
import com.cmux.service.strategy.UserCreationStrategy;
import com.cmux.service.strategy.UserRetrievalbyID;
import com.cmux.service.strategy.UserRetrievalbyName;

import java.util.List;

@Service
public class SubscriptionService {

    @Autowired
    private SubscriptionStrategy subscriptionStrategy;

	private UserRetrievalStrategy userRetrievalStrategy;

	@Autowired
	private UserCreationStrategy userCreationStrategy;

    public SubscriptionService(SubscriptionStrategy subscriptionStrategy, UserRetrievalStrategy userRetrievalStrategy, UserCreationStrategy userCreationStrategy) {
        this.subscriptionStrategy = subscriptionStrategy;
		this.userRetrievalStrategy = userRetrievalStrategy;
		this.userCreationStrategy = userCreationStrategy;
    }
	
	// Setter methods for runtime Subscription Strategy switching
	public void setSubscriptionStrategy(SubscriptionStrategy subscriptionStrategy) {
        this.subscriptionStrategy = subscriptionStrategy;
    }

	// Setter methods for runtime User Retrieval Strategy switching
	 public void setUserRetrievalStrategy(UserRetrievalStrategy userRetrievalStrategy) {
		this.userRetrievalStrategy = userRetrievalStrategy;
	}

	// Setter methods for runtime User Creation Strategy switching
	 public void setUserCreationStrategy(UserCreationStrategy userCreationStrategy) {
		this.userCreationStrategy = userCreationStrategy;
	}


    public void addSubscription(Long userId, Long subscriptionId) {
        subscriptionStrategy.addSubscription(userId, subscriptionId);
    }

    public void removeSubscription(Long userId, Long subscriptionId) {
        subscriptionStrategy.removeSubscription(userId, subscriptionId);
    }

    public List<User> getAllFollowers(Long userId) {
        return subscriptionStrategy.getAllFollowers(userId);
    }

    public List<User> getAllSubscriptions(Long userId) {
        return subscriptionStrategy.getAllSubscriptions(userId);
    }

    public List<User> getAllMutualSubscriptions(Long userId) {
        return subscriptionStrategy.getAllMutualSubscriptions(userId);
    }

    public boolean getHasSubscription(Long userId, Long otherUserId) {
        return subscriptionStrategy.getHasSubscription(userId, otherUserId);
    }

	public List<User> getUsers(String u) {
        // if name is all numbers, then search by userId
        if (u.matches("[0-9]+")) {
            userRetrievalStrategy = new UserRetrievalbyID();
        } else {
            userRetrievalStrategy = new UserRetrievalbyName();
        }
        return userRetrievalStrategy.getUsers(u);
	}

	public void createUser(Long userId, String name) {
		userCreationStrategy.createUser(userId, name);
	}


}
