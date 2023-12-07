package com.cmux.service.strategy.userretrieval;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.cmux.repository.UserRepository;
import com.cmux.entity.User;
import java.util.List;

@Component
public class UserRetrievalbyID implements UserRetrievalStrategy {

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<User> getUsers(String userIdString) {
        Long userId = Long.parseLong(userIdString);
        return userRepository.getUserByUserId(userId);
    }
    
}
