package com.cmux.service;

public interface SubscriptionStrategy {
    void execute(Long userId, Long otherUserId);
}
