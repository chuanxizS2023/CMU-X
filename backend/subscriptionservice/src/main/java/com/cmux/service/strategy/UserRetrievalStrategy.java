package com.cmux.service.strategy;

import java.util.List;
import com.cmux.entity.User;

public interface UserRetrievalStrategy {
    List<User> getUserByUserId(Long userId);
    List<User> getUsersByName(String name);
}
