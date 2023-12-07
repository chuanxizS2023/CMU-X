package com.cmux.service.strategy;

import com.cmux.entity.User;

public interface UserCreationStrategy {
    User createUser(Long userId, String name);
}
