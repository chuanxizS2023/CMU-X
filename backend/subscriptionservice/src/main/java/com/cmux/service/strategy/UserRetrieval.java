package com.cmux.service.strategy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cmux.repository.UserRepository;
import com.cmux.entity.User;
import java.util.List;

@Service
public class UserRetrieval implements UserRetrievalStrategy {

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<User> getUserByUserId(Long userId) {
        return userRepository.getUserByUserId(userId);
    }

    @Override
    public List<User> getUsersByName(String name) {
        return userRepository.getUsersByName(name);
    }
    
}
