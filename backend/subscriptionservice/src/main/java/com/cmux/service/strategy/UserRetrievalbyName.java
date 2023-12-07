package com.cmux.service.strategy;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.cmux.repository.UserRepository;
import com.cmux.entity.User;

@Component
public class UserRetrievalbyName implements UserRetrievalStrategy{

    @Autowired
    private UserRepository userRepository;
        
    @Override
    public List<User> getUsers(String username) {
        return userRepository.getUsersByName(username);
    }
    
}
