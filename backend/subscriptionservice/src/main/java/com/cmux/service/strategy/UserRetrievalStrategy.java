package com.cmux.service.strategy;

import java.util.List;
import com.cmux.entity.User;

public interface UserRetrievalStrategy {
    List<User> getUser(String u);

}
