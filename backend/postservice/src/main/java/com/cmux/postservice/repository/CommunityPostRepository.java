package com.cmux.postservice.repository;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.cmux.postservice.model.CommunityPost;

public interface CommunityPostRepository extends JpaRepository<CommunityPost, Long> {
    CommunityPost findByCommunityPostid(long communityPostid);
}
