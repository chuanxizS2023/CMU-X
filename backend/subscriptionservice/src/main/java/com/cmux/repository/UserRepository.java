package com.cmux.repository;


import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.cmux.entity.User;

import java.util.List;

@Repository
public interface UserRepository extends Neo4jRepository<User, Long> {

    // Create a user with userId and name
    @Query("MERGE (u:User {userId: $userId}) ON CREATE SET u.name = $name RETURN u")
    User createUser(@Param("userId") Long userId, @Param("name") String name);

    // Get all users to whom the user with userId is subscribed
    @Query("MATCH (u:User)-[:SUBSCRIBED_TO]->(sub:User) WHERE u.userId = $userId RETURN sub")
    List<User> findSubscriptionsByUserId(@Param("userId") Long userId);

    // Get all users who are following to the user with userId
    @Query("MATCH (u:User)<-[:SUBSCRIBED_TO]-(sub:User) WHERE u.userId = $userId RETURN sub")
    List<User> findFollowersByUserId(@Param("userId") Long userId);

    // Get all users who are subscribed to the user with userId and are also subscribed by the user with userId 
    @Query("MATCH (u:User)-[:SUBSCRIBED_TO]->(sub:User)-[:SUBSCRIBED_TO]->(u:User) WHERE u.userId = $userId RETURN sub")
    List<User> findMutualSubscriptionsByUserId(@Param("userId") Long userId);

    // Add a subscription (current user subscribes to another user)
    @Query("MATCH (u:User {userId: $userId}), (other:User {userId: $otherUserId}) " +
       "MERGE (u)-[:SUBSCRIBED_TO]->(other)")
       void addSubscription(@Param("userId") Long userId, @Param("otherUserId") Long otherUserId);

    // Add a subscriber (another user subscribes to the current user)
    @Query("MATCH (u:User {userId: $userId}), (other:User {userId: $otherUserId}) " +
       "MERGE (u)-[:SUBSCRIBED_BY]->(other)")
    void addSubscriber(@Param("userId") Long userId, @Param("otherUserId") Long otherUserId);

    // Remove a subscription (current user unsubscribes from another user)
    @Query("MATCH (u:User)-[r:SUBSCRIBED_TO]->(other:User) WHERE u.userId = $userId AND other.userId = $otherUserId " +
           "DELETE r")
    void removeSubscription(@Param("userId") Long userId, @Param("otherUserId") Long otherUserId);

    // Remove a subscriber (another user unsubscribes from the current user)
    @Query("MATCH (u:User)<-[r:SUBSCRIBED_TO]-(other:User) WHERE u.userId = $userId AND other.userId = $otherUserId " +
           "DELETE r")
    void removeSubscriber(@Param("userId") Long userId, @Param("otherUserId") Long otherUserId);
}
